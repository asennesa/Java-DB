package com.example.demo.models.dtoXML;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name= "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDtoXML> suppliers;

    public List<SupplierSeedDtoXML> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierSeedDtoXML> suppliers) {
        this.suppliers = suppliers;
    }
}
