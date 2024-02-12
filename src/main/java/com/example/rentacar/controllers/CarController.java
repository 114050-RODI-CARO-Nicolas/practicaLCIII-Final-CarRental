package com.example.rentacar.controllers;


import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.domain.Car;

import com.example.rentacar.services.implementations.CarServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarServiceImplementation carServiceImp;

    @GetMapping("/all")
    public List<Car> getAllCars(){
        return carServiceImp.getAlLCars();
    }

    @PostMapping("/new")
    public ResponseEntity<Car> registerCar(@RequestBody CarDTO carDTO){
        Car newCar= carServiceImp.registerCar(carDTO);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable long id, @RequestBody CarDTO carDTO){

        Car updatedCar = carServiceImp.updateCar(id, carDTO);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }









}
