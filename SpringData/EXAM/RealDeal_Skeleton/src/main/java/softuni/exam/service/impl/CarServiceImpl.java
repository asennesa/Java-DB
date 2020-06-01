package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.CARS_JSON_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        CarSeedDto[] carSeedDtos =
                this.gson.fromJson(new FileReader(GlobalConstants.CARS_JSON_PATH), CarSeedDto[].class);

        Arrays.stream(carSeedDtos).forEach(carSeedDto -> {
            if (this.validationUtil.isValid(carSeedDto)) {
                if (this.carRepository.findByMakeAndModelAndKilometers
                        (carSeedDto.getMake(),carSeedDto.getModel(),carSeedDto.getKilometers()) == null) {
                    Car car = this.modelMapper.map(carSeedDto, Car.class);
                    LocalDate localDate = LocalDate.parse(carSeedDto.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    car.setRegisteredOn(localDate);


                    sb.append(String.format("Successfully import car - %s - %s",carSeedDto.getMake()
                            ,carSeedDto.getModel()));
                    this.carRepository.saveAndFlush(car);
                } else {
                    sb.append("Already in DB.");

                }

            } else {
                sb.append("Invalid car");
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();
        this.carRepository.findAllOrderByPictureCountDescAndOrdOrderByMake()
                .forEach(p->{sb.append(String.format("Car make - %s,model -  %s" +
                                "\n\tKilometers - %d" +
                                "\n\tRegistered on - %s" +
                                "\n\tNumber of pictures - %d\n"
                        ,p.getMake()
                        ,p.getModel()
                        ,p.getKilometers()
                        ,p.getRegisteredOn().toString()
                        ,p.getPictures().size())).append(System.lineSeparator());

                });
        return sb.toString();
    }

    @Override
    public Car findUniqueCar(String make, String model, Integer kilometers) {
        return this.carRepository.findByMakeAndModelAndKilometers(make, model, kilometers);
    }

    @Override
    public Car findById(Integer id) {
        return this.carRepository.findById(id);
    }
}
