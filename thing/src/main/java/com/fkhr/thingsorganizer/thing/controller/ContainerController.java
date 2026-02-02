package com.fkhr.thingsorganizer.thing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fkhr.gisapi.GetFeatureLocationStreamRequestDto;
import com.fkhr.thingsorganizer.thing.dto.*;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.service.ContainerService;
import com.google.protobuf.InvalidProtocolBufferException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/containers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContainerController {
    @Autowired
    private ModelMapper modelMapper;
    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createContainer(@RequestBody ContainerCreateDto containerCreateDto) {
        Container container = modelMapper.map(containerCreateDto, Container.class);
        Container resultedContainer = containerService.save(container);
        return new ResponseEntity(resultedContainer, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateContainer(@RequestBody ContainerUpdateDto containerUpdateDto) {
        Container container = modelMapper.map(containerUpdateDto, Container.class);
        Container resultedContainer = containerService.save(container);
        return new ResponseEntity(resultedContainer, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity deleteContainer(@PathVariable("id")Long id) {
        containerService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getContainerById(@PathVariable("id")Long id) {
        Container container = containerService.load(id);
        return new ResponseEntity(container, HttpStatus.OK);
    }

    @PostMapping(value = "/parent-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getContainersByParentId(@RequestBody NullableIdDto filter) {
        List<Container> containers = containerService.getByParentId(filter.getId());
        return new ResponseEntity(containers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllContainers() {
        List<Container> containerList = containerService.findAll();
        return new ResponseEntity(containerList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchContainer(@RequestBody Container container,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        List<Container> containerList = containerService.search(container, page, size);
        return new ResponseEntity(containerList, HttpStatus.OK);
    }
    @PutMapping(value="/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateLocation(@RequestBody ContainerUpdateLocationDto containerUpdateLocationDto) throws InvalidProtocolBufferException, JsonProcessingException {
        UUID locationId = containerService.updateLocation(containerUpdateLocationDto.getContainerId(), containerUpdateLocationDto.getGeometry());
        return new ResponseEntity(new LocationIdDto(locationId), HttpStatus.OK);
    }

    @GetMapping("/container/{id}/location")
    public ResponseEntity getContainerLocation(@PathVariable("id") long containerId){
        ContainerLocationDto result = containerService.getContainerLocation(containerId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /*
    * Better to use the websocket implementation in GetContainersInAreaWebsocketHandler
    * */
    @Deprecated
    @PostMapping(value ="/area/location-stream", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getContainersInAreaStream(@RequestBody GeometryDto geometryDto) throws Exception {
        return containerService.getContainersInAreaStream(geometryDto);
    }

    @PostMapping(value = "/parent-id/location-stream", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getContainersLocationByParentId(@RequestBody NullableIdDto filter) {
        List<ContainerLocationDto> locations = containerService.getContainersLocationStreamByParentId(filter.getId());
        return new ResponseEntity(locations, HttpStatus.OK);
    }

    @GetMapping(value ="/container/{id}/location-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getContainerLocationStream(@PathVariable("id") Long id){
        return containerService.getContainerLocationStream(id);
    }

    @GetMapping(value="/container/{id}/history-location-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getContainerHistoryLocationStream(@PathVariable("id") Long id){
        return containerService.getContainerLocationHistoryStream(id);
    }
}
