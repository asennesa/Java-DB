package com.example.demo.services;

import com.example.demo.entities.Supplier;
import com.example.demo.models.dtoXML.SupplierSeedDtoXML;
import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.models.dtos.SupplierWriteDto;

import java.util.List;

public interface SupplierService {
   void seedSuppliers(List<SupplierSeedDtoXML>suppliers);
   Supplier getRandomSupplier();
   List<SupplierWriteDto> findAllLocalSuppliers();
}
