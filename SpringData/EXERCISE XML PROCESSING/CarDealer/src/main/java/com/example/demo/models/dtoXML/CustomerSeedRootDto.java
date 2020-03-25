package com.example.demo.models.dtoXML;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto {
    @XmlElement(name="customer")
    private List<CustomerSeedDtoXML> customers;


    public List<CustomerSeedDtoXML> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSeedDtoXML> customers) {
        this.customers = customers;
    }
}
