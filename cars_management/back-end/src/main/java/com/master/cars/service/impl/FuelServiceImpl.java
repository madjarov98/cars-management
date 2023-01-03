package com.master.cars.service.impl;

import com.master.cars.entity.Fuel;
import com.master.cars.repository.FuelRepository;
import com.master.cars.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelServiceImpl implements FuelService {

    private FuelRepository repository;

    @Override
    public List<Fuel> getAll() {
        return repository.findAll();
    }

    @Override
    public Fuel getById(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Fuel with id %d is not found", id));
        }

        return repository.getById(id);
    }

    @Override
    public Fuel create(Fuel body) {
        return repository.save(body);
    }

    @Override
    public Fuel update(Fuel body) {
        if (!repository.existsById(body.getId())) {
            throw new IllegalArgumentException(String.format("Fuel with id %d is not found", body.getId()));
        }

        return repository.save(body);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Fuel with id %d is not found", id));
        }

        repository.deleteById(id);
    }

    @Autowired
    public void setRepository(FuelRepository repository) {
        this.repository = repository;
    }
}
