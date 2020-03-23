package com.example.demo.services;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCustomers(CustomerSeedDto[] customerSeedDtos) {
        if(this.customerRepository.count() != 0){
            return;
        }
        Arrays.stream(customerSeedDtos)
                .forEach(CustomerSeedDto ->{
                    if(this.validationUtil.isValid(CustomerSeedDto)){
                        Customer customer=this.modelMapper.map(CustomerSeedDto,Customer.class);
                        LocalDateTime dateTime = LocalDateTime.parse(CustomerSeedDto.getBirthDate());
                        customer.setDateOfBirth(dateTime);
                        customer.setYoungDriver(CustomerSeedDto.isYoungDriver());
                        customerRepository.saveAndFlush(customer);

                    }else{
                        this.validationUtil.getViolations(CustomerSeedDto).stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                } );


    }
    @Override
    public Customer gerRandomCustomer() {
        Random random = new Random();
        long randomId = random
                .nextInt((int) this.customerRepository.count())+1;
        return this.customerRepository.getOne(randomId);
    }
}
