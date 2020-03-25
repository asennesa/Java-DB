package com.example.demo.services;

import com.example.demo.entities.Sale;
import com.example.demo.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;

    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;

    }

    @Override
    public void seedSales() {

        for (int i = 0; i < 20; i++) {
            Sale sale = new Sale();
            sale.setDiscount(this.setRandomDiscount());
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomer(this.customerService.gerRandomCustomer());
            this.saleRepository.saveAndFlush(sale);
        }


    }

    private Double setRandomDiscount() {
        Double[] discounts = new Double[]{0D,0.05,0.1,0.15,0.2,0.3,0.4,0.5};
        Random random = new Random();
        return discounts[random.nextInt(discounts.length)];
    }
}
