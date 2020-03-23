package com.example.demo.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {


    private String name;
    private LocalDateTime dateOfBirth;
    private boolean isYoungDriver;


    public Customer() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date_of_birth", nullable = false)
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    @Column(name = "is_young_driver", nullable = false)
    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }


}
