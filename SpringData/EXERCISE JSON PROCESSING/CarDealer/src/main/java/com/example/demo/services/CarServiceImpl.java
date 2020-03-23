package com.example.demo.services;

import com.example.demo.entities.Car;
import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.CarSeedDto;
import com.example.demo.repositories.CarRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final PartsService partsService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartsService partsService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.partsService = partsService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override

    public void seedCars(CarSeedDto[] carSeedDtos) {

        Arrays.stream(carSeedDtos)
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {
                        if (this.carRepository.findByMakeAndModelAndTravelledDistance(carSeedDto.getMake()
                                , carSeedDto.getModel(), carSeedDto.getTravelledDistance()) == null) {
                            Car car = this.modelMapper.map(carSeedDto, Car.class);
                            car.setParts(this.partsService.getRandomParts());
                            //MAPPING TABLE GETS FILLED WHEN YOU ADD THE ENTITIES TO THE OWNING SIDE
                            //IN THAT CASE IT'S CARS. (CHECK ENTITY PARTS) THE MANY TO MANY ANNOTATION IS
                            //MAPPED WITH THE NAME OF THE SET(PARTS SET IN CAR ENTITY) OF THE OWNING SIDE.

                            this.carRepository.saveAndFlush(car);

                        } else {
                            System.out.println("Already in DB.");

                        }
                    } else {
                        this.validationUtil.getViolations(carSeedDto).stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });

    }
    @Override
    public Car getRandomCar() {
        Random random = new Random();
        long randomId = random
                .nextInt((int) this.carRepository.count())+1;
        return this.carRepository.getOne(randomId);
    }
}
