package com.master.cars.entity;

import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fuels")
public class Fuel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public boolean hasIdInList(List<String> ids) {
        for (String id : ids) {
            if (Integer.parseInt(id) == this.id)
                return true;
        }

        return false;
    }
}
