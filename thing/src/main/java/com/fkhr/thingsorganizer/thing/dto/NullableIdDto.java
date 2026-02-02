package com.fkhr.thingsorganizer.thing.dto;

public class NullableIdDto {
    Long id;

    public NullableIdDto() {
    }

    public NullableIdDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
