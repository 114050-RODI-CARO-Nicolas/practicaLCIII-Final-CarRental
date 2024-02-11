package com.example.rentacar.DTOs;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
public class CarDTO implements Serializable{

    private String brand;
    private String model;
    private Long carTypeId;

}
