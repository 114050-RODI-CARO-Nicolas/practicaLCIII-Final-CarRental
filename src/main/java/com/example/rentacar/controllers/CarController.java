package com.example.rentacar.controllers;


import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.domain.Car;

import com.example.rentacar.exceptions.CurrentlyRentedCarException;
import com.example.rentacar.exceptions.ErrorResponse;
import com.example.rentacar.services.implementations.CarServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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


    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id){
        Car foundCar= carServiceImp.getCarById(id);
        return ResponseEntity.ok(foundCar);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, String>> deleteCar(@PathVariable long id)
    {
        HashMap<String, String> deleteStatus=new HashMap<>();

            carServiceImp.deleteCar(id);
           deleteStatus.put("Eliminado", "True");
           return ResponseEntity.ok(deleteStatus);


    }

    @ExceptionHandler(value = CurrentlyRentedCarException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCurrentlyRentedCarException (
            CurrentlyRentedCarException ex
    ) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), "CurrentlyRentedCarException");
    }













}
