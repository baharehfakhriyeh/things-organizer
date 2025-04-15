package com.fkhr.thingsorganizer.thing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fkhr.thingsorganizer.thing.dto.ThingCreateDto;
import com.fkhr.thingsorganizer.thing.dto.ThingUpdateContainerId;
import com.fkhr.thingsorganizer.thing.dto.ThingUpdateDto;
import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.proxy.ContentProxy;
import com.fkhr.thingsorganizer.thing.service.ThingService;
import com.fkhr.thingsorganizer.common.util.InfoProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/things", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {
    @Autowired
    private ModelMapper modelMapper;
    private final ThingService thingService;
    @Autowired
    public ThingController(ThingService thingService){
        this.thingService = thingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createThing(@RequestBody ThingCreateDto thingCreateDto) {
        Thing thing = modelMapper.map(thingCreateDto, Thing.class);
        Thing resultedThing = thingService.save(thing);
        return new ResponseEntity(resultedThing, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateThing(@RequestBody ThingUpdateDto thingUpdateDto) {
        Thing thing = modelMapper.map(thingUpdateDto, Thing.class);
        Thing resultedThing = thingService.save(thing);
        return new ResponseEntity(resultedThing, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity deleteThing(@PathVariable("id") Long id) {
        thingService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getThingById(@PathVariable("id") Long id) {
        Thing thing = thingService.load(id);
        return new ResponseEntity(thing, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllThings() {
        List<Thing> things = thingService.findAll();
        return new ResponseEntity(things, HttpStatus.OK);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchThing(@RequestBody Thing thingFilters,
                                      @RequestParam(defaultValue = "1")int page,
                                      @RequestParam(defaultValue = "10")int size) {
        List<Thing> things = thingService.search(thingFilters, page, size);
        return new ResponseEntity(things, HttpStatus.OK);
    }

    @PutMapping(value="/container", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateThingContainerId(@RequestBody ThingUpdateContainerId data) {
        Integer result = thingService.updateContainer(data.getOldContainerId(), data.getNewContainerId());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode updatedCount = objectMapper.createObjectNode();
        updatedCount.put("updated-count", result);
        return new ResponseEntity(updatedCount, HttpStatus.OK);
    }
}
