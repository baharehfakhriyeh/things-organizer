package com.fkhr.thingsorganizer.thing.dto;

import jakarta.validation.constraints.NotNull;

public class ContainerCreateDto {
    @NotNull
    String title;
    ContainerIdDto parent;

    public ContainerCreateDto(String title, ContainerIdDto parent) {
        this.title = title;
        this.parent = parent;
    }

    public ContainerCreateDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContainerIdDto getParent() {
        return parent;
    }

    public void setParent(ContainerIdDto parent) {
        this.parent = parent;
    }
}
