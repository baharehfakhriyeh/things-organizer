package com.fkhr.thingsorganizer.thing.dto;

public class ThingUpdateContainerId {
    Long oldContainerId;
    Long newContainerId;

    public ThingUpdateContainerId() {
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
