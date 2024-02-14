package com.example.rentacar.exceptions;

public class CurrentlyRentedCarException extends RuntimeException {

    private String message;
    public CurrentlyRentedCarException() {

    };

    public CurrentlyRentedCarException(String msg){
        super(msg);
        this.message="CurrentlyRentedCarException" + message;
    }


}

