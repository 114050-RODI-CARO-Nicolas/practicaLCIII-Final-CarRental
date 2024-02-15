package com.example.rentacar.exceptions;
import lombok.Getter;

@Getter
public class CurrentlyRentedCarException extends RuntimeException {

    private String message;
    public CurrentlyRentedCarException() {

    };

    public CurrentlyRentedCarException(String msg){
        super("CurrentlyRentedCarException: " + msg);
        this.message="CurrentlyRentedCarException: " + msg;
    }


}

