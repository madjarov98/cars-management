package com.master.cars.service.impl;

import com.master.cars.entity.Brand;
import com.master.cars.repository.BrandRepository;
import com.master.cars.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository repository;

    @Override
    public List<Brand> getAll() {
        return repository.findAll();
    }

    @Override
    public Brand getById(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Brand with id %d is not found", id));
        }

        return repository.getById(id);
    }

    @Override
    public Brand create(Brand body) {
        return repository.save(body);
    }

    @Override
    public Brand update(Brand body) {
        if (!repository.existsById(body.getId())) {
            throw new IllegalArgumentException(String.format("Brand with id %d is not found", body.getId()));
        }

        return repository.save(body);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Brand with id %d is not found", id));
        }

        repository.deleteById(id);
    }

    @Autowired
    public void setRepository(BrandRepository repository) {
        this.repository = repository;
    }
}
