package com.example.rentacar.services;
import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.domain.Car;
import org.apache.coyote.BadRequestException;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public interface ICarService {

     List<Car> getAlLCars();
     Car getCarById(long id);

     boolean checkAvailabilityByDate(long id, LocalDateTime dateToCheck);
     Car registerCar(CarDTO carDTO);
     Car updateCar(long carId, CarDTO carDTO);
     void deleteCar(long carId);





}
