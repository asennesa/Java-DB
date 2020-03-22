package com.example.demo.services;

import com.example.demo.models.dtos.PartSeedDto;

public interface PartsService {
    void seedParts(PartSeedDto[] partSeedDtos);
}
