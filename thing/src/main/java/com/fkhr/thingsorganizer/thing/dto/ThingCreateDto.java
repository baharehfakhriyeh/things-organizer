package com.fkhr.thingsorganizer.thing.dto;

import jakarta.validation.constraints.NotNull;

public class ThingCreateDto {
    @NotNull
    String title;
    @NotNull
    Long weight;
    ContainerIdDto container;

    public ThingCreateDto(String title, Long weight, ContainerIdDto container) {
        this.title = title;
        this.weight = weight;
        this.container = container;
    }

    public ThingCreateDto() {
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
