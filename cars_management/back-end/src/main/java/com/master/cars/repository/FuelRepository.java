package com.master.cars.repository;

import com.master.cars.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelRepository extends JpaRepository<Fuel, Integer> {
}
