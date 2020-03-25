package com.example.demo.services;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Part;
import com.example.demo.entities.Sale;
import com.example.demo.entities.Supplier;
import com.example.demo.models.dtoXML.CustomerSeedDtoXML;
import com.example.demo.models.dtoXML.CustomerViewDtoXML;
import com.example.demo.models.dtoXML.CustomerViewRootDtoXML;
import com.example.demo.models.dtos.CustomerSalesDataDto;
import com.example.demo.models.dtos.CustomerSeedDto;
import com.example.demo.models.dtos.CustomerWriteDto;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.utils.ValidationUtil;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    public void seedCustomers(List<CustomerSeedDtoXML> customers) {
        if (this.customerRepository.count() != 0) {
            return;
        }
        customers
                .forEach(customerSeedDtoXML -> {
                    if (this.validationUtil.isValid(customerSeedDtoXML)) {
                        Customer customer = this.modelMapper.map(customerSeedDtoXML, Customer.class);
                        System.out.println();
                        customerRepository.saveAndFlush(customer);

                    } else {
                        this.validationUtil.getViolations(customerSeedDtoXML).stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });


    }

    @Override
    public Customer gerRandomCustomer() {
        Random random = new Random();
        long randomId = random
                .nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.getOne(randomId);
    }

    @Override
    public CustomerViewRootDtoXML findAllByBirthDateOrderedAsc() {
        CustomerViewRootDtoXML customerViewRootDtoXML = new CustomerViewRootDtoXML();
        List<CustomerViewDtoXML> customers = this.customerRepository.
                findAllByDateOfBirthAndYoungDriver()
                .stream()
                .map(e -> {
                    CustomerViewDtoXML customerViewDtoXML
                            = this.modelMapper.map(e, CustomerViewDtoXML.class);
                    return customerViewDtoXML;

                }).collect(Collectors.toList());
        customerViewRootDtoXML.setCustomers(customers);
        System.out.println();

        return customerViewRootDtoXML;

    }

    @Override
    public List<CustomerSalesDataDto> findAllCustomersWithSalesData(int greater) {
        return this.customerRepository.findAllBySalesGreaterThan(greater).stream()
                .map(e -> {
                    CustomerSalesDataDto customerSalesDataDto = new CustomerSalesDataDto();
                    customerSalesDataDto.setFullName(e.getName());
                    customerSalesDataDto.setBoughtCars(e.getSales().size());
                    BigDecimal totalSpentMoney = new BigDecimal(0);

                    for (Sale sale : e.getSales()) {
                        Set<Part> parts = sale.getCar().getParts();
                        List<BigDecimal> partsPrices = new ArrayList<>();
                        parts.forEach(p -> partsPrices.add(p.getPrice()));
                        BigDecimal result = partsPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                        totalSpentMoney = totalSpentMoney.add(result);
                    }
                    System.out.println();
                    customerSalesDataDto.setSpentMoney(totalSpentMoney);
                    return customerSalesDataDto;
                }).collect(Collectors.toList()).stream().sorted((e1, e2) -> {
                    int sort = e2.getSpentMoney().compareTo(e1.getSpentMoney());
                    if (sort == 0) {
                        sort = Integer.compare(e2.getBoughtCars(), e1.getBoughtCars());
                    }
                    return sort;
                }).collect(Collectors.toList());
    }
}
