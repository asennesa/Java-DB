package com.example.demo.models.dtoXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name ="cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDto {
    @XmlElement(name = "car")
    private List<CarSeedDtoXML> cars;

    public List<CarSeedDtoXML> getCars() {
        return cars;
    }

    public void setCars(List<CarSeedDtoXML> cars) {
        this.cars = cars;
    }
}
