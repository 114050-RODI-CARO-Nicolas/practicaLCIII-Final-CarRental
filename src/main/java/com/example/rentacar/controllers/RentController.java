package com.example.rentacar.controllers;


import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.DTOs.RentDTO;
import com.example.rentacar.domain.Car;
import com.example.rentacar.domain.Rent;
import com.example.rentacar.services.RentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    RentServiceImplementation rentServiceImp;

    @GetMapping("/all")
    public List<Rent> getAllCars(){
        return rentServiceImp.getAllRents();
    }

    @PostMapping("/new")
    public ResponseEntity<Rent> registerCar(@RequestBody RentDTO rentDTO){
        Rent newRent= rentServiceImp.registerRent(rentDTO);
        return new ResponseEntity<>(newRent, HttpStatus.CREATED);
    }




}
