package com.fkhr.thingsorganizer.thing.service;

import com.fkhr.thingsorganizer.common.service.BaseService;
import com.fkhr.thingsorganizer.thing.model.Thing;

import java.util.List;

public interface ThingService extends BaseService<Thing, Long> {

    List<Thing> search(Thing thingFilters, int page, int size);
    Integer updateContainer(Long oldContainerId, Long newContainerId);
}
