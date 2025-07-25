package com.fkhr.thingsorganizer.thing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.common.exeptionhandling.RestExceptionHandler;
import com.fkhr.thingsorganizer.thing.dto.ContainerIdDto;
import com.fkhr.thingsorganizer.thing.dto.ThingCreateDto;
import com.fkhr.thingsorganizer.thing.dto.ThingUpdateContainerIdDto;
import com.fkhr.thingsorganizer.thing.dto.ThingUpdateDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.service.ThingService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ThingController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(RestExceptionHandler.class)
class ThingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ThingService thingService;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    private Thing thing;

    @BeforeEach
    public void setup(){
        thing = new Thing(
                1L,
                "pen",
                5L,
                new Container(
                        1L,
                        "red bag",
                        null
                )
        );
    }
//todo: write negative test scenarios
    @Test
    public void givenThingCreateDto_whenCreateThing_thenReturnSavedThing() throws Exception {
        ThingCreateDto thingCreateDto = new ThingCreateDto(
                "pen",
                5L,
                new ContainerIdDto(
                        1L
                )
        );
        Thing mappedThing = new Thing(
                null,
                "pen",
                5L,
                new Container(
                        1L,
                        null,
                        null
                )
        );

        BDDMockito.given(modelMapper.map(ArgumentMatchers.any(ThingCreateDto.class), ArgumentMatchers.eq(Thing.class)))
                .willReturn(mappedThing);

        BDDMockito.given(thingService.save(ArgumentMatchers.any(Thing.class))).willReturn(thing);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/things")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingCreateDto)));

        resultActions
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(thing.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(thing.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", CoreMatchers.is(thing.getWeight().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.container.id", CoreMatchers.is(thing.getContainer().getId().intValue())));
    }

    @Test
    public void givenThingUpdateDto_whenUpdateThing_thenReturnUpdatedThing() throws Exception {
        ThingUpdateDto thingUpdateDto = new ThingUpdateDto(
                1L,
                "pen",
                5L,
                new ContainerIdDto(
                        1L
                )
        );
        Thing mappedThing = new Thing(
                1L,
                "pen",
                5L,
                new Container(
                        1L,
                        null,
                        null
                )
        );

        BDDMockito.given(modelMapper.map(ArgumentMatchers.any(ThingUpdateDto.class), ArgumentMatchers.eq(Thing.class)))
                .willReturn(mappedThing);
        BDDMockito.given(thingService.save(ArgumentMatchers.any(Thing.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/things")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingUpdateDto)));

        resultActions
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(thingUpdateDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(thingUpdateDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight", CoreMatchers.is(thingUpdateDto.getWeight().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.container.id", CoreMatchers.is(thingUpdateDto.getContainer().getId().intValue())));
    }

    @Test
    void givenThingId_whenDeleteThing_thenReturnNothing() throws Exception {
        Long thingId = 1L;
        BDDMockito.willDoNothing().given(thingService).delete(thingId);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/things/id/{id}", thingId));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenThingId_whenGetThingById_thenReturnThing() throws Exception {
        Long thingId = 1L;
        BDDMockito.given(thingService.load(thingId)).willReturn(thing);
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
        BDDMockito.given(thingService.load(thingId)).willThrow(customExeption);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/things/id/{id}", thingId));
        result.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(customExeption.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", CoreMatchers.is(customExeption.getMessage())));
    }

    @Test
    void givenThingList_whenGetAllThings_thenRetrunThingList() throws Exception {
        Thing thing2 = new Thing(
                2L,
                "pencil",
                3L,
                new Container(
                        1L,
                        "red bag",
                        null
                )
        );
        List<Thing> thingList = new ArrayList<>();
        thingList.add(thing);
        thingList.add(thing2);
        BDDMockito.given(thingService.findAll()).willReturn(thingList);
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
                2L,
                "pencil",
                3L,
                new Container(
                        1L,
                        "red bag",
                        null
                )
        );
        List<Thing> things = new ArrayList<>();
        things.add(thing);
        things.add(thing2);
        BDDMockito.given(thingService.search(ArgumentMatchers.any(Thing.class),
                ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).willReturn(things);
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
        ThingUpdateContainerIdDto thingUpdateContainerIdDto = new ThingUpdateContainerIdDto(
                1L, 2L
        );
        Integer updatedThingCount = 2;
        BDDMockito.given(thingService.updateContainer(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .willReturn(updatedThingCount);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/things/container")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thingUpdateContainerIdDto)));
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("updated-count", CoreMatchers.is(updatedThingCount)));
    }
}