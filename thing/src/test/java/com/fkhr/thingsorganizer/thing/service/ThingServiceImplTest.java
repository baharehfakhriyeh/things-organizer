package com.fkhr.thingsorganizer.thing.service;

import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.repository.ThingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ThingServiceImplTest {
    ThingService thingService;
    @Mock
    ThingRepository thingRepository;
    @Mock
    ContainerService containerService;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        thingService = new ThingServiceImpl(thingRepository, containerService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void load() {
        Thing thing = new Thing();
        Long inputId = 1L;
        Mockito.when(thingRepository.findById(inputId)).thenReturn(Optional.of(thing));
        Thing result = thingService.load(inputId);
        assertEquals(result, thing);
        Mockito.verify(thingRepository, Mockito.times(1)).findById(inputId);
    }

    @Test
    void loadNotFoundException() {
        Long inputId = 0L;
        Mockito.when(thingRepository.findById(inputId)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomExeption.class, () -> thingService.load(inputId));

        Mockito.verify(thingRepository, Mockito.times(1)).findById(inputId);
    }
}