package com.fkhr.thingsorganizer.thing.dto;

import jakarta.validation.constraints.NotNull;

public class ThingUpdateDto {
    @NotNull
    Long id;
    @NotNull
    String title;
    @NotNull
    Long weight;
    ContainerIdDto container;

    public ThingUpdateDto(Long id, String title, Long weight, ContainerIdDto container) {
        this.id = id;
        this.title = title;
        this.weight = weight;
        this.container = container;
    }

    public ThingUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public ContainerIdDto getContainer() {
        return container;
    }

    public void setContainer(ContainerIdDto container) {
        this.container = container;
    }
}
