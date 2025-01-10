package com.fkhr.thingsorganizer.thing.service;

import com.fkhr.thingsorganizer.common.service.BaseService;
import com.fkhr.thingsorganizer.thing.model.Container;

import java.util.List;

public interface ContainerService extends BaseService<Container, Long> {
    List<Container> search(Container container, int page, int size);
}
