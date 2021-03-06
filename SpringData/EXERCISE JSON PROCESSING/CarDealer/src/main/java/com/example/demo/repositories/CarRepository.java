package com.example.demo.repositories;

import com.example.demo.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    Car findByMakeAndModelAndTravelledDistance(String name, String model, Long distance);
    List<Car>findByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
