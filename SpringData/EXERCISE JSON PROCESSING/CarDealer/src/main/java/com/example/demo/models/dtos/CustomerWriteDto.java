package com.example.demo.models.dtos;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class CustomerWriteDto {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String dateOfBirth;
    @Expose
    private boolean isYoungDriver;

    public CustomerWriteDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
