package com.example.demo.services;

import com.example.demo.entities.Customer;
import com.example.demo.models.dtos.CustomerSalesDataDto;
import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.models.dtos.CustomerWriteDto;

import java.util.List;

public interface CustomerService {
    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    Customer gerRandomCustomer();

    List<CustomerWriteDto> findAllByBirthDateOrderedAsc();

    List<CustomerSalesDataDto>findAllCustomersWithSalesData(int greater);


}
