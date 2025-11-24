package com.fkhr.thingsorganizer.thing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fkhr.thingsorganizer.thing.dto.ContainerCreateDto;
import com.fkhr.thingsorganizer.thing.dto.ContainerUpdateDto;
import com.fkhr.thingsorganizer.thing.dto.ContainerUpdateLocationDto;
import com.fkhr.thingsorganizer.thing.dto.LocationIdDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.service.ContainerService;
import com.google.protobuf.InvalidProtocolBufferException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/location")
    public ResponseEntity updateLocation(@RequestBody ContainerUpdateLocationDto containerUpdateLocationDto) throws InvalidProtocolBufferException, JsonProcessingException {
        UUID locationId = containerService.updateLocation(containerUpdateLocationDto.getContainerId(), containerUpdateLocationDto.getGeometry());
        return new ResponseEntity(new LocationIdDto(locationId), HttpStatus.OK);
    }


}
