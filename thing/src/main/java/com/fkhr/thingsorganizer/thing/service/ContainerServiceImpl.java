package com.fkhr.thingsorganizer.thing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.gisapi.*;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.thing.dto.GeometryDto;
import com.fkhr.thingsorganizer.thing.dto.LocationPropertiesDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.repository.ContainerRepository;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.tomcat.util.threads.VirtualThreadExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class ContainerServiceImpl implements ContainerService {

    private final ContainerRepository containerRepository;
    private final ThingService thingService;
    @GrpcClient("featureService")
    private FeatureServiceGrpc.FeatureServiceBlockingStub featureServiceBlockingStub;
    @GrpcClient("featureService")
    private FeatureServiceGrpc.FeatureServiceStub featureServiceStub;

    @Autowired
    public ContainerServiceImpl(ContainerRepository containerRepository, ThingService thingService) {
        this.containerRepository = containerRepository;
        this.thingService = thingService;
    }

    @Override
    public Container save(Container container) {
        Container parentContainer = null;
        if (container.getParent() != null && container.getParent().getId() != null) {
            parentContainer = loadParent(container.getParent().getId());
        }
        Container result = null;
        if (container.getId() == null) {
            result = containerRepository.save(container);
        } else {
            if (!exists(container.getId())) {
                throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
            } else {
                result = containerRepository.save(container);
            }
        }
        if (result != null && parentContainer != null) {
            result.setParent(parentContainer);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        Container container = load(id);
        if (container == null) {
            throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
        } else {
            thingService.updateContainer(container.getId(), null);
            containerRepository.delete(container);
        }
    }

    @Override
    public Container load(Long id) {
        Optional<Container> result = containerRepository.findById(id);
        if (result.equals(Optional.empty())) {
            throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
        } else {
            return result.get();
        }
    }

    private Container loadParent(Long parentId) {
        Container result = containerRepository.findById(parentId).get();
        if (result == null) {
            throw new CustomExeption(CustomError.PARENT_CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
        } else {
            return result;
        }
    }

    @Override
    public List<Container> findAll() {
        return containerRepository.findAll();
    }

    @Override
    public List<Container> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return containerRepository.findAll(pageable).toList();
    }

    public List<Container> search(Container container, int page, int size) {
        return null;
    }

    @Override
    public UUID updateLocation(long containerId, GeometryDto geometry) throws JsonProcessingException, InvalidProtocolBufferException {
        Container container = load(containerId);
        Geometry geometryObj = convertGeometryDtoToProtoGeometry(geometry);
        LocationPropertiesDto locationPropertiesDto = new LocationPropertiesDto(containerId, EntityType.CONTAINER);
        Struct properties = convertPropertiesDtoToProtoStruct(locationPropertiesDto);
        CreateFeatureRequestDto request = CreateFeatureRequestDto.newBuilder()
                .setOwner(getContainerAsOwner(containerId))
                .setDescription(container.getTitle())
                .setGeometry(geometryObj)
                .setProperties(properties)
                .build();
        FeatureResponseDto featureResponseDto = featureServiceBlockingStub.createFeature(request);
        UUID locationId = UUID.fromString(featureResponseDto.getId());
        return locationId;
    }

    @Override
    public SseEmitter getContainerLocationStream(long containerId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        SseEmitter sseEmitter = new SseEmitter();
        GetFeatureLocationStreamRequestDto requestDto = GetFeatureLocationStreamRequestDto.newBuilder()
                .setOwner(getContainerAsOwner(containerId)).build();
        executorService.submit(() -> {
            featureServiceStub.getFeatureLocationStream(requestDto, new StreamObserver<FeatureResponseDto>() {
                @Override
                public void onNext(FeatureResponseDto featureResponseDto) {
                    try {
                        String featureResponseJson = JsonFormat.printer().print(featureResponseDto);
                        sseEmitter.send(SseEmitter.event().data(featureResponseJson));
                    } catch (Exception e) {
                        sseEmitter.completeWithError(e);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    sseEmitter.completeWithError(throwable);
                }

                @Override
                public void onCompleted() {
                    sseEmitter.complete();
                }
            });
        });
        return sseEmitter;
    }

    @Override
    public SseEmitter getContainerLocationHistoryStream(long containerId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        SseEmitter sseEmitter = new SseEmitter();
        GetFeatureLocationStreamRequestDto requestDto = GetFeatureLocationStreamRequestDto.newBuilder()
                .setOwner(getContainerAsOwner(containerId)).build();
        executorService.submit(() -> {
            featureServiceStub.getFeatureLocationHistoryStream(requestDto, new StreamObserver<FeatureResponseDto>() {
                @Override
                public void onNext(FeatureResponseDto featureResponseDto) {
                    try {
                        String featureResponseJson = JsonFormat.printer().print(featureResponseDto);
                        sseEmitter.send(SseEmitter.event().data(featureResponseJson));
                    } catch (Exception e) {
                        sseEmitter.completeWithError(e);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    sseEmitter.completeWithError(throwable);
                }

                @Override
                public void onCompleted() {
                    sseEmitter.complete();
                }
            });
        });
        return sseEmitter;
    }

    private String getContainerAsOwner(Long containerId){
        return EntityType.CONTAINER + "-" + containerId;
    }

    private static Struct convertPropertiesDtoToProtoStruct(LocationPropertiesDto locationPropertiesDto) throws JsonProcessingException, InvalidProtocolBufferException {
        ObjectMapper mapper = new ObjectMapper();
        String propertiesJson = mapper.writeValueAsString(locationPropertiesDto);
        Struct.Builder propertiesBuilder = Struct.newBuilder();
        JsonFormat.parser().merge(propertiesJson, propertiesBuilder);
        Struct properties = propertiesBuilder.build();
        return properties;
    }

    private static Geometry convertGeometryDtoToProtoGeometry(GeometryDto geometry) throws InvalidProtocolBufferException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String geometryJson = mapper.writeValueAsString(geometry);
        Geometry.Builder geometryBuilder = Geometry.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(geometryJson, geometryBuilder);
        Geometry geometryObj = geometryBuilder.build();
        return geometryObj;
    }

    public boolean exists(Long id) {
        try {
            Container container = load(id);
            if (container != null)
                return true;
            return false;
        } catch (CustomExeption exeption) {
            if (exeption.getCode().equals(CustomError.CONTAINER_NOT_FOUND.code()))
                return false;
            else
                throw exeption;
        }
    }
}
