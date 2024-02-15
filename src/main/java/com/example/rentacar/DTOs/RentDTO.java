package com.example.rentacar.DTOs;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class RentDTO implements Serializable {

    private long carId;
    private Integer rentedDays;
    private LocalDateTime startRent;
    private LocalDateTime endRent;


}
