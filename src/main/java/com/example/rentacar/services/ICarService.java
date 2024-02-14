package com.example.rentacar.services;
import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.domain.Car;

import java.util.List;

public interface ICarService {

     List<Car> getAlLCars();
     Car getCarById(long id);
     Car registerCar(CarDTO carDTO);
     Car updateCar(long carId, CarDTO carDTO);
     void deleteCar(long carId);



}
