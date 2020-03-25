package com.example.demo.models.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class CarSeedDto {
     @Expose
     private String make;
     @Expose
     private String model;
     @Expose
     private Long travelledDistance;

     public CarSeedDto() {
     }

     public String getMake() {
          return make;
     }

     public void setMake(String make) {
          this.make = make;
     }

     public String getModel() {
          return model;
     }

     public void setModel(String model) {
          this.model = model;
     }
     @Min(value=0)
     public Long getTravelledDistance() {
          return travelledDistance;
     }

     public void setTravelledDistance(Long travelledDistance) {
          this.travelledDistance = travelledDistance;
     }
}
