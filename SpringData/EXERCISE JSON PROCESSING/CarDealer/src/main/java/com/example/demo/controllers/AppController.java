package com.example.demo.controllers;


import com.example.demo.constants.GlobalConstants;
import com.example.demo.models.dtos.CarSeedDto;
import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.models.dtos.PartSeedDto;
import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.services.*;
import com.example.demo.utils.FileUtil;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static com.example.demo.constants.GlobalConstants.SUPPLIERS_FILE_PATH;


@Component
public class AppController implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartsService partsService;
    private final CarService carService;
    private final CustomerService customerService;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final SaleService saleService;

    public AppController(SupplierService supplierService, PartsService partsService, CarService carService, CustomerService customerService, Gson gson, FileUtil fileUtil, SaleService saleService) {
        this.supplierService = supplierService;
        this.partsService = partsService;
        this.carService = carService;
        this.customerService = customerService;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();

    }

    private void seedSales() {
        this.saleService.seedSales();

    }

    private void seedCustomers() throws FileNotFoundException {
        CustomerSeedDto[] dtos =
                this.gson.fromJson(new FileReader(GlobalConstants.CUSTOMERS_FILE_PATH), CustomerSeedDto[].class);

        this.customerService.seedCustomers(dtos);
    }

    private void seedCars() throws FileNotFoundException {
        CarSeedDto[] dtos =
                this.gson.fromJson(new FileReader(GlobalConstants.CARS_FILE_PATH), CarSeedDto[].class);
        this.carService.seedCars(dtos);
    }

    private void seedParts() throws FileNotFoundException {
        PartSeedDto[] dtos =
                this.gson.fromJson(new FileReader(GlobalConstants.PARTS_FILE_PATH), PartSeedDto[].class);
        this.partsService.seedParts(dtos);
    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierSeedDto[] dtos =
                this.gson.fromJson(new FileReader(SUPPLIERS_FILE_PATH), SupplierSeedDto[].class);
        this.supplierService.seedSuppliers(dtos);
    }
}