package com.master.cars.controller;

import com.master.cars.entity.Brand;
import com.master.cars.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(BrandController.BASE_PATH)
public class BrandController {

    public static final String BASE_PATH = "/api/v1/brands";
    public static final String ID_PATH = BASE_PATH + "/{id}";

    private BrandService brandService;

    @GetMapping("/all")
    public List<Brand> getAll() {
        return brandService.getAll();
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return ResponseEntity
                .ok(brandService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Brand brand) {
        return ResponseEntity
                .ok(brandService.create(brand));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Brand brand) {
        return ResponseEntity
                .ok(brandService.update(brand));
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        brandService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }
}


