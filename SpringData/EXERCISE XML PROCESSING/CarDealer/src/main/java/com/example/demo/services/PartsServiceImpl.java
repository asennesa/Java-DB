package com.example.demo.services;

import com.example.demo.entities.Part;
import com.example.demo.models.dtoXML.PartSeedDtoXML;
import com.example.demo.models.dtos.PartSeedDto;
import com.example.demo.repositories.PartRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.*;

@Service
@Transactional
public class PartsServiceImpl implements PartsService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;

    @Autowired
    public PartsServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
    }

    @Override

    public void seedParts(List<PartSeedDtoXML> parts) {
        if (this.partRepository.count() != 0) {
            return;
        }
                parts
                        .forEach(partSeedDtoXML -> {
                    if (this.validationUtil.isValid(partSeedDtoXML)) {
                        Part part = this.modelMapper.map(partSeedDtoXML, Part.class);
                        part.setSupplier(this.supplierService.getRandomSupplier());
                        Random random = new Random();
                        int randomNum = random.nextInt(2);
                        System.out.println();
                        this.partRepository.saveAndFlush(part);
                    } else {
                        this.validationUtil.getViolations(partSeedDtoXML).stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });


    }

    @Override
    public Set<Part> getRandomParts() {
        Random random = new Random();
        Set<Part>resultSet = new HashSet<>();
        int randomCounter = random.nextInt(10)+10;
        for (int i = 0; i < randomCounter; i++) {
            long randomId = random.nextInt((int) this.partRepository.count())+1;
            resultSet.add(this.partRepository.getOne(randomId));

        }
        return resultSet;
    }
}
