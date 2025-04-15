package com.fkhr.thingsorganizer.common.service;

import java.util.List;

public interface BaseService<T, I> {
    T save(T t);
    void delete(I id);
    T load(I id);
    List<T> findAll();
    List<T> findAll(int page, int size);
    boolean exists(I id);
}
