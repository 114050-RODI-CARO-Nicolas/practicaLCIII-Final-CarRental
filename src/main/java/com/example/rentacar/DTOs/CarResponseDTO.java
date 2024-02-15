package com.example.rentacar.DTOs;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class CarResponseDTO implements Serializable {
    private Long id;
    private String brand;
    private String model;
    private Long carTypeId;
}
