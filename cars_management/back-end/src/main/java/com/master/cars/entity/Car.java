package com.master.cars.entity;

import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "model", length = 100, nullable = false)
    private String model;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "horse_power")
    private Integer horsePower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    @Transient
    public boolean hasModelNameInList(List<String> models) {
        for (String model : models) {
            if (model.equals(this.model))
                return true;
        }

        return false;
    }
}
