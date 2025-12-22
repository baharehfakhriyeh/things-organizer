package com.fkhr.thingsorganizer.thing.service;

import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.repository.ThingRepository;
import com.fkhr.thingsorganizer.thing.util.MonitoringMetrics;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ThingServiceImplTest {
    @Mock
    private ThingRepository thingRepository;

    private ThingServiceImpl thingService;
    @Mock
    private ContainerServiceImpl containerService;
    @Mock
    private RestClient restClient;
    @Mock
    private RestClient.Builder restClientBuilder;
    @Mock
    private MonitoringMetrics metrics;
    private Thing thing;
    private Container container;

    @BeforeEach
    void setUp() {
        restClientBuilder = Mockito.mock(RestClient.Builder.class, Mockito.RETURNS_DEEP_STUBS);
        restClient = Mockito.mock(RestClient.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(restClientBuilder.build()).thenReturn(restClient);
        thingService = new ThingServiceImpl(thingRepository, containerService, restClientBuilder, metrics);
        thingService = Mockito.spy(thingService);

        container = new Container(
                1l,
                "red bag",
                null
        );
        thing = new Thing(
                1l,
                "pen",
                5l,
                new Container(
                        1l,
                        null,
                        null
                )
        );
    }

    @Test
    void givenThingObject_whenSave_thenReturnSavedThingWithCompleteContainerObject() {
        thing.setId(null);
        BDDMockito.given(containerService.load(thing.getContainer().getId()))
                .willReturn(container);
        BDDMockito.given(thingRepository.save(thing)).willReturn(thing);
        Thing savedThing = thingService.save(thing);
        Assertions.assertThat(savedThing).isNotNull();
        Assertions.assertThat(thing.getContainer()).isEqualTo(container);
    }

    @Test
    void givenThingObject_whenSave_thenReturnUpdatedThingWithCompleteContainerObject(){
        BDDMockito.given(containerService.load(thing.getContainer().getId()))
                .willReturn(container);
        BDDMockito.doReturn(thing).when(thingService).load(thing.getId());
        BDDMockito.given(thingRepository.save(thing)).willReturn(thing);
        Thing updatedThing = thingService.save(thing);
        Assertions.assertThat(updatedThing).isNotNull();
        Assertions.assertThat(thing.getContainer()).isEqualTo(container);
    }

    @Test
    void givenThingObject_whenSave_thenThrowsContainerNotFoundException(){
        CustomExeption customExeption = new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
        BDDMockito.given(containerService.load(thing.getContainer().getId())).willThrow(customExeption);
        CustomExeption result = org.junit.jupiter.api.Assertions.assertThrows(CustomExeption.class, () -> {
            thingService.save(thing);
        });
        Assertions.assertThat(result.getCode()).isEqualTo(customExeption.getCode());
        BDDMockito.verify(thingRepository, Mockito.never()).save(BDDMockito.any(Thing.class));
    }

    @Test
    void givenThingObject_whenSave_thenThrowsThingNotFoundException(){
        CustomExeption customExeption = new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
        BDDMockito.given(containerService.load(thing.getContainer().getId())).willReturn(container);
        BDDMockito.doThrow(customExeption).when(thingService).load(thing.getId());
        CustomExeption result = org.junit.jupiter.api.Assertions.assertThrows(CustomExeption.class, () ->{
            thingService.save(thing);
        });
        Assertions.assertThat(result.getCode()).isEqualTo(customExeption.getCode());
        BDDMockito.verify(thingRepository, Mockito.never()).save(BDDMockito.any(Thing.class));
    }

    @Test
    void givenThingId_whenDelete_thenNothing() {
        long thingId = 1l;
        BDDMockito.doReturn(thing).when(thingService).load(thingId);

        BDDMockito.willDoNothing().given(thingRepository).delete(thing);
        thingService.delete(thingId);
        BDDMockito.verify(thingRepository, Mockito.times(1)).delete(thing);
    }

    @Test
    void givenInvalidThingId_whenDelete_thenThrowsThingNotFound() {
        long thingId = 10l;
        BDDMockito.doReturn(null).when(thingService).load(thingId);
        CustomExeption customExeption = new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
        CustomExeption result = org.junit.jupiter.api.Assertions.assertThrows(CustomExeption.class, () ->{
            thingService.delete(thingId);
        });
       Assertions.assertThat(result.getCode()).isEqualTo(customExeption.getCode());
    }

    @Test
    void givenThingId_whenLoad_thenReturnThing() {
        long thingId = 1l;
        BDDMockito.given(thingRepository.findById(thingId)).willReturn(Optional.of(thing));
        Thing result = thingService.load(thingId);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(thing);
    }

    @Test
    void givenThingId_whenLoad_thenThrowsCustomException() {
        long thingId = 10l;
        BDDMockito.given(thingRepository.findById(thingId)).willReturn(Optional.empty());
        CustomExeption customExeption = new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
        CustomExeption result = org.junit.jupiter.api.Assertions.assertThrows(CustomExeption.class, () ->{
            thingService.load(thingId);
        });
        Assertions.assertThat(result.getCode()).isEqualTo(customExeption.getCode());
    }

    @Test
    void givenNothing_whenFindAll_thenReturnThingList() {
        Thing thing1 = new Thing(
                2l,
                "pen",
                5l,
                new Container(
                        1l,
                        null,
                        null
                )
        );
        List<Thing> thingList = new ArrayList<>();
        thingList.add(thing);
        thingList.add(thing1);
        BDDMockito.given(thingRepository.findAll()).willReturn(thingList);
        List<Thing> result = thingService.findAll();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturnThingListSecondPage() {
        int page = 1;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);
        Thing thing1 = new Thing(
                2l,
                "pen",
                5l,
                new Container(
                        1l,
                        null,
                        null
                )
        );
        List<Thing> thingList = new ArrayList<>();
        thingList.add(thing1);
        Page<Thing> thingPage = new PageImpl<>(thingList, pageable, thingList.size());
        BDDMockito.given(thingRepository.findAll(pageable)).willReturn(thingPage);
        List<Thing> result = thingService.findAll(page, size);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result).containsExactly(thing1);
    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturnEmptyPage() {
        int page = 2;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);
        BDDMockito.given(thingRepository.findAll(pageable)).willReturn(Page.empty(pageable));
        List<Thing> result = thingService.findAll(page, size);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(0);
    }

    @Test
    void givenThingId_whenExists_thenReturnTrue() {
        long thingId = 1;
        BDDMockito.doReturn(thing).when(thingService).load(thingId);
        boolean result = thingService.exists(thingId);
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void givenThingId_whenExists_thenReturnFalse() {
        long thingId = 10;
        CustomExeption customExeption = new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
        BDDMockito.doThrow(customExeption).when(thingService).load(thingId);
        boolean result = thingService.exists(thingId);
        Assertions.assertThat(result).isEqualTo(false);
    }

    @Test
    void givenThingFiltersAndPageAndSize_whenSearch_thenReturnThingList() {
        int page = 0;
        int size = 1;
        Pageable pageable = PageRequest.of(page, size);
        Thing thing1 = new Thing(
                2l,
                "pen",
                5l,
                new Container(
                        1l,
                        null,
                        null
                )
        );
        Thing thingFilters = new Thing(
                null,
                "bag",
                null,
                null
        );
        List<Thing> thingList = new ArrayList<>();
        thingList.add(thing);
        BDDMockito.given(thingRepository.searchThings(thingFilters.getTitle(), thingFilters.getWeight(),
                thingFilters.getContainer() != null ? thingFilters.getContainer().getId() : null,
                thingFilters.getContainer() != null ? thingFilters.getContainer().getTitle() : null, pageable)).willReturn(thingList);
        List<Thing> result = thingService.search(thingFilters, page, size);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result).containsExactly(thing);
    }

    @Test
    void givenOldContainerIdAndNull_whenUpdateContainer_thenRetrunUpdatedContainerCount() {
        Long oldContainerId = 1L;
        Long newContainerId = null;

        BDDMockito.given(thingRepository.updateContainer(oldContainerId, newContainerId)).willReturn(1);
        int result = thingService.updateContainer(oldContainerId, newContainerId);
        Assertions.assertThat(result).isEqualTo(1);

    }

    @Test
    void givenOldContainerIdAndNewContainerId_whenUpdateContainer_thenRetrunUpdatedContainerCount() {
        Long oldContainerId = 1L;
        Long newContainerId = 10L;

        BDDMockito.given(containerService.exists(newContainerId)).willReturn(true);
        BDDMockito.given(thingRepository.updateContainer(oldContainerId, newContainerId)).willReturn(1);
        int result = thingService.updateContainer(oldContainerId, newContainerId);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void givenOldContainerIdAndNewContainerId_whenUpdateContainer_thenRetrunContainerNotFoundException() {
        Long oldContainerId = 1L;
        Long newContainerId = 10L;
        CustomExeption customExeption = new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
        BDDMockito.given(containerService.exists(newContainerId)).willReturn(false);
        CustomExeption result = org.junit.jupiter.api.Assertions.assertThrows(
                CustomExeption.class, () -> thingService.updateContainer(oldContainerId, newContainerId));
        Assertions.assertThat(result.getCode()).isEqualTo(customExeption.getCode());
    }
}