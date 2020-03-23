package com.example.demo.services;

import com.example.demo.entities.Customer;
import com.example.demo.models.dtos.CustomerSeedDto;

public interface CustomerService {
    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    Customer gerRandomCustomer();
}
