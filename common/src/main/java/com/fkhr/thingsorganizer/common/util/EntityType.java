package com.fkhr.thingsorganizer.common.util;

public enum EntityType {
    THING(1),
    CONTAINER(2),
    CONTENT(3);
    int value;
    private EntityType(int value){
        this.value = value;
    }

}
