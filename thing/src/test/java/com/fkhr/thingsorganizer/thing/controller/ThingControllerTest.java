package com.fkhr.thingsorganizer.thing.controller;

import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.service.ThingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class ThingControllerTest {
    ThingController thingController;
    MockMvc mockMvc;
    @Mock
    ThingService thingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        thingController = new ThingController(thingService);
        mockMvc = MockMvcBuilders.standaloneSetup(thingController).build();
    }

    @Test
    void getThingById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/thing/id/"+1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}