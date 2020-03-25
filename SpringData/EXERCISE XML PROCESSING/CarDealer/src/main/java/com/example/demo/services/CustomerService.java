package com.example.demo.services;

import com.example.demo.entities.Customer;
import com.example.demo.models.dtoXML.CustomerSeedDtoXML;
import com.example.demo.models.dtoXML.CustomerViewRootDtoXML;
import com.example.demo.models.dtos.CustomerSalesDataDto;
import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.models.dtos.CustomerWriteDto;

import java.util.List;

public interface CustomerService {
    void seedCustomers(List<CustomerSeedDtoXML>customers);

    Customer gerRandomCustomer();

    CustomerViewRootDtoXML findAllByBirthDateOrderedAsc();

    List<CustomerSalesDataDto>findAllCustomersWithSalesData(int greater);


}
