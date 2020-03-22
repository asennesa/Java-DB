package com.example.demo.services;

import com.example.demo.models.dtos.PartSeedDto;
import com.example.demo.repositories.PartRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PartsServiceImpl implements PartsService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PartsServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedParts(PartSeedDto[] partSeedDtos) {
        

    }
}
