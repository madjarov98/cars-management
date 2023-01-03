package com.master.cars.service;

import com.master.cars.Filter;
import com.master.cars.entity.Car;

import java.util.List;

public interface CarService extends Service<Car> {
    List<Car> findFiltered(List<Filter> filters);
}
