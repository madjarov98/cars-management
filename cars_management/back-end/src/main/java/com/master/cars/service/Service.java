package com.master.cars.service;

import java.util.List;

public interface Service<T> {
    List<T> getAll();

    T getById(Integer id);

    T create(T body);

    T update(T body);

    void delete(Integer id);
}
