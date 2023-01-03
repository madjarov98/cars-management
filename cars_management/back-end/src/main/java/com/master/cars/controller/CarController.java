package com.master.cars.controller;

import com.master.cars.Filter;
import com.master.cars.entity.Car;
import com.master.cars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(CarController.BASE_PATH)
public class CarController {

    public static final String BASE_PATH = "/api/v1/cars";
    public static final String ID_PATH = BASE_PATH + "/{id}";
    private CarService carService;

    @PostMapping("/all")
    public ResponseEntity<?> getFiltered(@RequestBody List<Filter> filters) {

        return ResponseEntity
                .ok(carService.findFiltered(filters));
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return ResponseEntity
                .ok(carService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Car car) {
        return ResponseEntity
                .ok(carService.create(car));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Car car) {
        return ResponseEntity
                .ok(carService.update(car));
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        carService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}


