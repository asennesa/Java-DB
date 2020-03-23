package com.example.demo.services;

import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.SupplierSeedDto;

public interface SupplierService {
   void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);
   Supplier getRandomSupplier();
}
