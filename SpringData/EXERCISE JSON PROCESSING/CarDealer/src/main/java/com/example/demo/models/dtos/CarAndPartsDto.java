package com.example.demo.models.dtos;

import com.example.demo.entities.Car;
import com.example.demo.entities.Part;
import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarAndPartsDto {
    @Expose
    private CarSeedDto car;
    @Expose
    private Set<PartDto> parts;

    public CarAndPartsDto() {
    }

    public CarSeedDto getCar() {
        return car;
    }

    public void setCar(CarSeedDto car) {
        this.car = car;
    }

    public Set<PartDto> getParts() {
        return parts;
    }

    public void setParts(Set<PartDto> parts) {
        this.parts = parts;
    }
}
