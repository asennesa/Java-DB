package com.example.demo.services;

import com.example.demo.entities.Car;
import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.CarSeedDto;

public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);

    Car getRandomCar();
}
