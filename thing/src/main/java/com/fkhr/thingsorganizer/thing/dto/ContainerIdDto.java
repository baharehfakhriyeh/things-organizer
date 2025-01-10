package com.fkhr.thingsorganizer.thing.dto;

import jakarta.validation.constraints.NotNull;

public class ContainerIdDto {
    @NotNull
    Long id;

    public ContainerIdDto(Long id) {
        this.id = id;
    }

    public ContainerIdDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
