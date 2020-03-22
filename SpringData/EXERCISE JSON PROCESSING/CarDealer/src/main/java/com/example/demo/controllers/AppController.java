package com.example.demo.controllers;


import com.example.demo.constants.GlobalConstants;
import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.services.SupplierService;
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
    private final Gson gson;
    private final FileUtil fileUtil;

    public AppController(SupplierService supplierService, Gson gson, FileUtil fileUtil) {
        this.supplierService = supplierService;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedSuppliers();

    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierSeedDto[] dtos =
                this.gson.fromJson(new FileReader(SUPPLIERS_FILE_PATH), SupplierSeedDto[].class);
        this.supplierService.seedSuppliers(dtos);
    }
}