package com.example.demo.models.dtoXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootDtoXML {
    @XmlElement(name = "customer")
    List<CustomerViewDtoXML> customers;

    public CustomerViewRootDtoXML() {
    }

    public List<CustomerViewDtoXML> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerViewDtoXML> customers) {
        this.customers = customers;
    }
}
