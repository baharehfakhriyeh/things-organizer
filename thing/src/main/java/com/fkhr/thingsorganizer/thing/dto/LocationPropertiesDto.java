package com.fkhr.thingsorganizer.thing.dto;

import com.fkhr.thingsorganizer.common.util.EntityType;

public class LocationPropertiesDto {
    long id;
    EntityType entityType;

    public LocationPropertiesDto() {
    }

    public LocationPropertiesDto(long id, EntityType entityType) {
        this.id = id;
        this.entityType = entityType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
