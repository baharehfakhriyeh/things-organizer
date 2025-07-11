package com.fkhr.thingsorganizer.thing.dto;

public class ThingUpdateContainerIdDto {
    Long oldContainerId;
    Long newContainerId;

    public ThingUpdateContainerIdDto() {
    }

    public ThingUpdateContainerIdDto(Long oldContainerId, Long newContainerId) {
        this.oldContainerId = oldContainerId;
        this.newContainerId = newContainerId;
    }

    public Long getOldContainerId() {
        return oldContainerId;
    }

    public void setOldContainerId(Long oldContainerId) {
        this.oldContainerId = oldContainerId;
    }

    public Long getNewContainerId() {
        return newContainerId;
    }

    public void setNewContainerId(Long newContainerId) {
        this.newContainerId = newContainerId;
    }
}
