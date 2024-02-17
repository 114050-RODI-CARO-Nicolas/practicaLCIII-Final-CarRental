package com.example.rentacar.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentForCreationDTO implements Serializable {


    private Long carId;
    private Integer rentedDays;
    private LocalDateTime startRent;



}
