package com.example.rentacar.controllers;


import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.DTOs.CarResponseDTO;
import com.example.rentacar.domain.Car;

import com.example.rentacar.exceptions.CurrentlyRentedCarException;
import com.example.rentacar.exceptions.BadRequestException;
import com.example.rentacar.exceptions.ErrorResponse;
import com.example.rentacar.services.implementations.CarServiceImplementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarServiceImplementation carServiceImp;

    @GetMapping("/all")
    public ResponseEntity<List<CarResponseDTO>>  getAllCars(){
        List<Car> cars= carServiceImp.getAlLCars();
        List<CarResponseDTO> carResponseDTOList= new ArrayList<CarResponseDTO>();


        for (Car car:cars)
        {
            CarResponseDTO carResponseDTO=new CarResponseDTO();
            carResponseDTO.setId(car.getId());
            carResponseDTO.setCarTypeId(car.getCarType().getId());
            carResponseDTO.setBrand(car.getBrand());
            carResponseDTO.setModel(car.getModel());
            carResponseDTOList.add(carResponseDTO);
        }
        return ResponseEntity.ok(carResponseDTOList);

    }


    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable long id){
        Car foundCar= carServiceImp.getCarById(id);
        CarResponseDTO foundCarDTO=new CarResponseDTO();
        /*TODO:
        Aplicar ModelMapper
         */
        foundCarDTO.setCarTypeId(foundCar.getCarType().getId());
        foundCarDTO.setBrand(foundCar.getBrand());
        foundCarDTO.setModel(foundCar.getModel());
        return ResponseEntity.ok(foundCarDTO);
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
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    };
    @ExceptionHandler(value= BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException (
            BadRequestException ex
    ) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
