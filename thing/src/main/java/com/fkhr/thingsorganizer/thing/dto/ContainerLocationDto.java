package com.fkhr.thingsorganizer.thing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContainerLocationDto {
    long containerId;
    GeometryDto geometry;
    LocalDateTime timestamp;

    public ContainerLocationDto() {
    }

    public ContainerLocationDto(long containerId, GeometryDto geometry, LocalDateTime timestamp) {
        this.containerId = containerId;
        this.geometry = geometry;
        this.timestamp = timestamp;
    }

    public long getContainerId() {
        return containerId;
    }

    public void setContainerId(long containerId) {
        this.containerId = containerId;
    }

    public GeometryDto getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDto geometry) {
        this.geometry = geometry;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
