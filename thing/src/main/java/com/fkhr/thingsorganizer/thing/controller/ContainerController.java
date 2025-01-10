package com.fkhr.thingsorganizer.thing.controller;

import com.fkhr.thingsorganizer.thing.dto.ContainerCreateDto;
import com.fkhr.thingsorganizer.thing.dto.ContainerUpdateDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.service.ContainerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/container", produces = MediaType.APPLICATION_JSON_VALUE)
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


}
