package com.master.cars.service.impl;

import com.master.cars.Filter;
import com.master.cars.entity.Car;
import com.master.cars.repository.CarRepository;
import com.master.cars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository repository;

    @Override
    public List<Car> findFiltered(List<Filter> filters) {
        List<Car> all = repository.findAll();

        if (filters == null || filters.isEmpty()) {
            return all;
        }

        List<Car> filtered = new ArrayList<>();

        for (Car car : all) {
            boolean match = true;

            for (Filter filter : filters) {
                switch (filter.getName()) {
                    case "brand":
                        if (!car.getBrand().hasIdInList(filter.getValues()))
                            match = false;
                        break;
                    case "fuel":
                        if (!car.getFuel().hasIdInList(filter.getValues()))
                            match = false;
                        break;
                    case "model":
                        if (!car.hasModelNameInList(filter.getValues()))
                            match = false;
                        break;
                }
            }

            if (match)
                filtered.add(car);
        }

        return filtered;
    }

    @Override
    public List<Car> getAll() {
        return repository.findAll();
    }

    @Override
    public Car getById(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Car with id %d is not found", id));
        }

        return repository.getById(id);
    }

    @Override
    public Car create(Car body) {
        return repository.save(body);
    }

    @Override
    public Car update(Car body) {
        if (!repository.existsById(body.getId())) {
            throw new IllegalArgumentException(String.format("Car with id %d is not found", body.getId()));
        }

        return repository.save(body);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Car with id %d is not found", id));
        }

        repository.deleteById(id);
    }

    @Autowired
    public void setRepository(CarRepository repository) {
        this.repository = repository;
    }
}
