package com.fkhr.thingsorganizer.thing.dto;

import jakarta.validation.constraints.NotNull;

public class ContainerUpdateDto {
    @NotNull
    Long id;
    @NotNull
    String title;
    ContainerIdDto parent;

    public ContainerUpdateDto(Long id, String title, ContainerIdDto parent) {
        this.id = id;
        this.title = title;
        this.parent = parent;
    }

    public ContainerUpdateDto() {
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

    public ContainerIdDto getParent() {
        return parent;
    }

    public void setParent(ContainerIdDto parent) {
        this.parent = parent;
    }
}
