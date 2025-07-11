package com.fkhr.thingsorganizer.thing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.thingsorganizer.commonsecurity.service.UserServiceImpl;
import com.fkhr.thingsorganizer.thing.service.ThingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class ContainerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ThingService thingService;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ModelMapper modelMapper;

}
