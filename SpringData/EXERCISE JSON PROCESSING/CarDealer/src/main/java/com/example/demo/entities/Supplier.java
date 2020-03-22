package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
    private String name;
    private Boolean isImporter;



    public Supplier() {
    }
    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="is_importer")
    public Boolean getIsImporter() {
        return isImporter;
    }

    public void setIsImporter(Boolean is_importer) {
        this.isImporter = is_importer;
    }

}
