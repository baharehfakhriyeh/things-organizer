package com.fkhr.thingsorganizer.thing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fkhr.thingsorganizer.common.service.BaseService;
import com.fkhr.thingsorganizer.thing.dto.GeometryDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

public interface ContainerService extends BaseService<Container, Long> {
    List<Container> search(Container container, int page, int size);
    UUID updateLocation(long containerId, GeometryDto geometry) throws JsonProcessingException, InvalidProtocolBufferException;
    SseEmitter getContainerLocationStream(long containerId);
    SseEmitter getContainerLocationHistoryStream(long containerId);
}
