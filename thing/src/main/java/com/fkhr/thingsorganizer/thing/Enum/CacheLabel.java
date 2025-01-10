package com.fkhr.thingsorganizer.thing.Enum;

public enum CacheLabel {
    THING("thing"),
    THING_LIST("thingList");
    private CacheLabel(String value){
        this.value = value;
    }
    private final String value;
}
