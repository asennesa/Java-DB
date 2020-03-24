package com.example.demo.services;

import com.example.demo.entities.Car;
import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.CarAndPartsDto;
import com.example.demo.models.dtos.CarSeedDto;
import com.example.demo.models.dtos.CarWriteDto;

import java.util.List;

public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);

    Car getRandomCar();

    List<CarWriteDto> findByMakeOrderByModelDistance();

    List<CarAndPartsDto> findAllCarsWithParts();
}
