package com.example.demo.controllers;


import com.example.demo.constants.GlobalConstants;
import com.example.demo.models.dtoXML.*;
import com.example.demo.models.dtos.*;
import com.example.demo.services.*;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.XmlParser;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.example.demo.constants.GlobalConstants.EX1_FILE_PATH;


@Component
public class AppController implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartsService partsService;
    private final CarService carService;
    private final CustomerService customerService;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final SaleService saleService;
    private final XmlParser xmlParser;

    @Autowired
    public AppController(SupplierService supplierService, PartsService partsService, CarService carService, CustomerService customerService, Gson gson, FileUtil fileUtil, SaleService saleService, XmlParser xmlParser) {
        this.supplierService = supplierService;
        this.partsService = partsService;
        this.carService = carService;
        this.customerService = customerService;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.saleService = saleService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedSuppliers();
        this.seedParts();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();
        this.findAllByDateOfBirth();
//        this.findToyotaCars();
//        this.findSuppliersThatDoNotImportPartsFromAbroad();
//        this.findCarsAndParts();
       // this.findCustomerAndSales();
    }

    private void findCustomerAndSales() throws IOException {
        List<CustomerSalesDataDto> customerSalesDataDtosthis = this.customerService.findAllCustomersWithSalesData(0);
        String json = this.gson.toJson(customerSalesDataDtosthis);
        this.fileUtil.write(json, GlobalConstants.EX5_FILE_PATH);

    }

    private void findCarsAndParts() throws IOException {
        List<CarAndPartsDto> carAndPartsDtos = this.carService.findAllCarsWithParts();
        System.out.println();
        String json = this.gson.toJson(carAndPartsDtos);
        this.fileUtil.write(json, GlobalConstants.EX4_FILE_PATH);

    }

    private void findSuppliersThatDoNotImportPartsFromAbroad() throws IOException {
        List<SupplierWriteDto> dtos = this.supplierService.findAllLocalSuppliers();
        String json = this.gson.toJson(dtos);
        this.fileUtil.write(json, GlobalConstants.EX3_FILE_PATH);

    }

    private void findToyotaCars() throws IOException {
        List<CarWriteDto> carWriteDtos = this.carService.findByMakeOrderByModelDistance();
        String json = this.gson.toJson(carWriteDtos);
        this.fileUtil.write(json, GlobalConstants.EX2_FILE_PATH);

    }

    private void findAllByDateOfBirth() throws IOException, JAXBException {
        CustomerViewRootDtoXML customerViewRootDtoXML = this.customerService.findAllByBirthDateOrderedAsc();
        this.xmlParser.marshalToFile(EX1_FILE_PATH,customerViewRootDtoXML);

    }

    private void seedSales() {
        this.saleService.seedSales();

    }

    private void seedCustomers() throws FileNotFoundException, JAXBException {
        CustomerSeedRootDto customerSeedRootDto =
                this.xmlParser.unmarshalFromFile(GlobalConstants.CUSTOMERS_FILE_PATH,CustomerSeedRootDto.class);
        System.out.println();
        this.customerService.seedCustomers(customerSeedRootDto.getCustomers());


    }

    private void seedCars() throws FileNotFoundException, JAXBException {
        CarSeedRootDto carSeedRootDto =
                this.xmlParser.unmarshalFromFile
                        (GlobalConstants.CARS_FILE_PATH,CarSeedRootDto.class);
        this.carService.seedCars(carSeedRootDto.getCars());
    }

    private void seedParts() throws FileNotFoundException, JAXBException {
        PartSeedRootDto partSeedRootDto =
                this.xmlParser.unmarshalFromFile
                        (GlobalConstants.PARTS_FILE_PATH, PartSeedRootDto.class);
        System.out.println();
        this.partsService.seedParts(partSeedRootDto.getParts());
    }

    private void seedSuppliers() throws FileNotFoundException, JAXBException {
        SupplierSeedRootDto supplierSeedRootDto =
                this.xmlParser.unmarshalFromFile
                        (GlobalConstants.SUPPLIERS_FILE_PATH, SupplierSeedRootDto.class);
        this.supplierService.seedSuppliers(supplierSeedRootDto.getSuppliers());
        System.out.println();


    }


}