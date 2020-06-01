package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CarService carService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.PICTURES_JSON_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        PictureSeedDto[] pictureSeedDtos =
                this.gson.fromJson(new FileReader(GlobalConstants.PICTURES_JSON_PATH), PictureSeedDto[].class);
        System.out.println();

        Arrays.stream(pictureSeedDtos).forEach(pictureSeedDto -> {
            if (this.validationUtil.isValid(pictureSeedDto)) {
                if (this.pictureRepository.findByName(pictureSeedDto.getName()) == null) {
                    Picture picture = this.modelMapper.map(pictureSeedDto, Picture.class);
                    LocalDateTime date = LocalDateTime.parse(pictureSeedDto.getDateAndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    picture.setDateAndTime(date);
                    if (this.carService.findById(pictureSeedDto.getCar()) == null) {
                        sb.append("Invalid picture");
                    }else{
                        Car car = this.carService.findById(pictureSeedDto.getCar());
                        picture.setCar(car);
                        sb.append(String.format("Successfully import picture - %s",pictureSeedDto.getName()));
                        this.pictureRepository.saveAndFlush(picture);
                    }
                    Car car = this.carService.findById(pictureSeedDto.getCar());


                } else {
                    sb.append("Already in DB.");

                }

            } else {
                sb.append("Invalid picture");
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }

    @Override
    public Picture findByName(String name) {
        return this.pictureRepository.findByName(name);
    }
}
