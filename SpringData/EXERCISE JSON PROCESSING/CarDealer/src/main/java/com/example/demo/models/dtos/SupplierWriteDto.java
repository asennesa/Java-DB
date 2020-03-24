package com.example.demo.models.dtos;

import com.google.gson.annotations.Expose;

public class SupplierWriteDto {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private int partsCount;

    public SupplierWriteDto() {
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

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
