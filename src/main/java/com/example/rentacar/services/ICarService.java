package com.example.rentacar.services;
import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.domain.Car;

import java.util.List;

public interface ICarService {

    public List<Car> getAlLCars();
    public Car registerCar(CarDTO carDTO);


}
