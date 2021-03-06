package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    private Double discount;
    private Customer customer;
    private Car car;


    public Sale() {
    }

    @Column(name = "discounts")
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @OneToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne()
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
