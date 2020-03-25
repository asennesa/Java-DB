package com.example.demo.models.dtoXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name ="parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedRootDto {
    @XmlElement(name = "part")
    private List<PartSeedDtoXML> parts;


    public List<PartSeedDtoXML> getParts() {
        return parts;
    }

    public void setParts(List<PartSeedDtoXML> parts) {
        this.parts = parts;
    }
}
