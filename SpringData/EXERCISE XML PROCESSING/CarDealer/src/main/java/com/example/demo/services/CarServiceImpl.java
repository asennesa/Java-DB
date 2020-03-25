package com.example.demo.services;

import com.example.demo.entities.Car;
import com.example.demo.entities.Supplier;
import com.example.demo.models.dtoXML.CarSeedDtoXML;
import com.example.demo.models.dtos.CarAndPartsDto;
import com.example.demo.models.dtos.CarSeedDto;
import com.example.demo.models.dtos.CarWriteDto;
import com.example.demo.models.dtos.PartDto;
import com.example.demo.repositories.CarRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

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

    public void seedCars(List<CarSeedDtoXML>cars) {

        cars
                .forEach(carSeedDtoXML -> {
                    if (this.validationUtil.isValid(carSeedDtoXML)) {
                        if (this.carRepository.findByMakeAndModelAndTravelledDistance(carSeedDtoXML.getMake()
                                , carSeedDtoXML.getModel(), carSeedDtoXML.getTravelledDistance()) == null) {
                            Car car = this.modelMapper.map(carSeedDtoXML, Car.class);
                            car.setParts(this.partsService.getRandomParts());
                            //MAPPING TABLE GETS FILLED WHEN YOU ADD THE ENTITIES TO THE OWNING SIDE
                            //IN THAT CASE IT'S CARS. (CHECK ENTITY PARTS) THE MANY TO MANY ANNOTATION IS
                            //MAPPED WITH THE NAME OF THE SET(PARTS SET IN CAR ENTITY) OF THE OWNING SIDE.

                            this.carRepository.saveAndFlush(car);

                        } else {
                            System.out.println("Already in DB.");

                        }
                    } else {
                        this.validationUtil.getViolations(carSeedDtoXML).stream()
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

    @Override
    public List<CarWriteDto> findByMakeOrderByModelDistance() {
        return this.carRepository.findByMakeOrderByModelAscTravelledDistanceDesc("Toyota").stream()
                .map(e->{
                    CarWriteDto carWriteDto = this.modelMapper.map(e,CarWriteDto.class);
                    return carWriteDto;

                }).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<CarAndPartsDto> findAllCarsWithParts() {
        List<CarAndPartsDto> list= this.carRepository.findAll().stream()
                .map(e->{
                    CarAndPartsDto carAndPartsDto = new CarAndPartsDto();
                    carAndPartsDto.setCar(this.modelMapper.map(e,CarSeedDto.class));
                    Set<PartDto> partDtoSet  = new HashSet<>();
                    e.getParts().forEach(p->{
                        partDtoSet.add(this.modelMapper.map(p,PartDto.class));
                    });
                    carAndPartsDto.setParts(partDtoSet);
                    return carAndPartsDto;

                }).collect(Collectors.toList());
        System.out.println();
        return list;

    }
}
