package com.master.cars.controller;

import com.master.cars.entity.Fuel;
import com.master.cars.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(FuelController.BASE_PATH)
public class FuelController {

    public static final String BASE_PATH = "/api/v1/fuels";
    public static final String ID_PATH = BASE_PATH + "/{id}";

    private FuelService fuelService;

    @GetMapping("/all")
    public List<Fuel> getAll() {
        return fuelService.getAll();
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return ResponseEntity
                .ok(fuelService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Fuel brand) {
        return ResponseEntity
                .ok(fuelService.create(brand));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Fuel brand) {
        return ResponseEntity
                .ok(fuelService.update(brand));
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        fuelService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @Autowired
    public void setFuelService(FuelService fuelService) {
        this.fuelService = fuelService;
    }
}
