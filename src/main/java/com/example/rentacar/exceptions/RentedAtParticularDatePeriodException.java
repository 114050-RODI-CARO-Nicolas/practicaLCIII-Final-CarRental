package com.example.rentacar.exceptions;

import lombok.Getter;

@Getter
public class RentedAtParticularDatePeriodException extends RuntimeException  {

    private String message;
    public RentedAtParticularDatePeriodException () {

    };

    public RentedAtParticularDatePeriodException(String msg) {
        super("RentedAtParticularDatePeriodException: " + msg);
        this.message="RentedAtParticularDatePeriodException: " + msg;
    }
}
