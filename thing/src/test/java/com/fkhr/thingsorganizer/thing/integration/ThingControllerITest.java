package com.fkhr.thingsorganizer.thing.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.thing.config.TestSecurityConfig;
import com.fkhr.thingsorganizer.thing.dto.ContainerIdDto;
import com.fkhr.thingsorganizer.thing.dto.ThingCreateDto;
import com.fkhr.thingsorganizer.thing.dto.ThingUpdateContainerIdDto;
import com.fkhr.thingsorganizer.thing.dto.ThingUpdateDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.repository.ContainerRepository;
import com.fkhr.thingsorganizer.thing.repository.ThingRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestSecurityConfig.class)
public class ThingControllerITest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ThingRepository thingRepository;
    @Autowired
    private ContainerRepository containerRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private Thing thing;
    private Container container;

    @BeforeEach
    public void setup(){
        thingRepository.deleteAll();
        containerRepository.deleteAll();
        container = new Container(
                "red bag",
                null
        );
        containerRepository.save(container);
        thing = new Thing(null,
                "pen",
                5l,
                container
        );
    }

    @AfterAll
    public void afterAll() {
        thingRepository.deleteAll();
        containerRepository.deleteAll();
    }

    @Test
    public void givenThingCreateDto_whenCreateThing_thenReturnSavedThing() throws Exception {
        ThingCreateDto thingCreateDto = new ThingCreateDto(
                "pen",
                5L,
                new ContainerIdDto(
                        container.getId()
                )
        );

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/things")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingCreateDto)));

        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(thing.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", CoreMatchers.is(thing.getWeight().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.container.id", CoreMatchers.is(thing.getContainer().getId().intValue())));
    }

    @Test
    public void givenThingUpdateDto_whenUpdateThing_thenReturnUpdatedThing() throws Exception {
        thingRepository.save(thing);
        ThingUpdateDto thingUpdateDto = new ThingUpdateDto(
                thing.getId(),
                "pencil",
                6L,
                new ContainerIdDto(
                        container.getId()
                )
        );

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/things")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingUpdateDto)));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(thingUpdateDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(thingUpdateDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", CoreMatchers.is(thingUpdateDto.getWeight().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.container.id", CoreMatchers.is(thingUpdateDto.getContainer().getId().intValue())));
    }

    @Test
    void givenThingId_whenDeleteThing_thenReturnNothing() throws Exception {
        thingRepository.save(thing);
        Long thingId = thing.getId();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/things/id/{id}", thingId));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenThingId_whenGetThingById_thenReturnThing() throws Exception {
        thingRepository.save(thing);
        Long thingId = thing.getId();
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/things/id/{id}", thingId));
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(thing.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(thing.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", CoreMatchers.is(thing.getWeight().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.container.id", CoreMatchers.is(thing.getContainer().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.container.title", CoreMatchers.is(thing.getContainer().getTitle())));
    }

    @Test
    void givenInvalidThingId_whenGetThingById_thenReturnThingNotFoundError() throws Exception {
        Long thingId = 10L;
        CustomExeption customExeption = new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/things/id/{id}", thingId));
        result.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(customExeption.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", CoreMatchers.is(customExeption.getMessage())));
    }

    @Test
    void givenThingList_whenGetAllThings_thenRetrunThingList() throws Exception {
        Thing thing2 = new Thing(
                null,
                "pencil",
                3L,
                container
        );
        thingRepository.save(thing);
        thingRepository.save(thing2);
        List<Thing> thingList = new ArrayList<>();
        thingList.add(thing);
        thingList.add(thing2);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/things"));
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(thingList.size())));

    }

    @Test
    void givenFiltersAndPageAndSize_whenSearchThing_thenReturnThingList() throws Exception {
        int page = 0;
        int size = 2;
        Thing thingFilters = new Thing(
                null,
                "pen",
                null,
                null
        );
        Thing thing2 = new Thing(
                null,
                "pencil",
                3L,
                container
        );
        thingRepository.save(thing);
        thingRepository.save(thing2);
        List<Thing> things = new ArrayList<>();
        things.add(thing);
        things.add(thing2);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/things/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingFilters))
                .queryParam("page", objectMapper.writeValueAsString(page))
                .queryParam("size", objectMapper.writeValueAsString(size)));
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(things.size())));

    }

    @Test
    void givenThingUpgateContainerIdDto_whenUpdateThingContainerId_thenUpdatedThingCount() throws Exception {
        thingRepository.save(thing);
        Container container2 = new Container(
                "red bag",
                null
        );
        containerRepository.save(container2);
        ThingUpdateContainerIdDto thingUpdateContainerIdDto = new ThingUpdateContainerIdDto(
                container.getId(), container2.getId()
        );
        Integer updatedThingCount = 1;
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/things/container")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingUpdateContainerIdDto)));
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("updated-count", CoreMatchers.is(updatedThingCount)));
    }
}
