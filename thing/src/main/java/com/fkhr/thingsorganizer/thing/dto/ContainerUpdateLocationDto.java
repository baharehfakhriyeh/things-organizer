package com.fkhr.thingsorganizer.thing.dto;


public class ContainerUpdateLocationDto {
    long containerId;
    GeometryDto geometry;

    public ContainerUpdateLocationDto() {
    }

    public ContainerUpdateLocationDto(long containerId, GeometryDto geometry) {
        this.containerId = containerId;
        this.geometry = geometry;
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
}
