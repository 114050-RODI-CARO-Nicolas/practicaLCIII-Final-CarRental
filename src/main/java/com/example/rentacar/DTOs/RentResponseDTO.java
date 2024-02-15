package com.example.rentacar.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentResponseDTO implements Serializable {

    private Long rentId;
    private String brand;
    private String model;
    private Integer rentedDays;
    private LocalDateTime startRent;
    private LocalDateTime endRent;
    private BigDecimal totalPrice;
}
