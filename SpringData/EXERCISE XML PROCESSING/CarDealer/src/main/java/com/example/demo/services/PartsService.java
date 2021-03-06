package com.example.demo.services;

import com.example.demo.entities.Part;
import com.example.demo.models.dtoXML.PartSeedDtoXML;
import com.example.demo.models.dtos.PartSeedDto;

import java.util.List;
import java.util.Set;

public interface PartsService {
    void seedParts(List<PartSeedDtoXML> parts);
    Set<Part> getRandomParts();
}
