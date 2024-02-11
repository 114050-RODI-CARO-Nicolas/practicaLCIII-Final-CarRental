package com.example.rentacar.services.implementations;

import com.example.rentacar.DTOs.CarDTO;
import com.example.rentacar.domain.Car;
import com.example.rentacar.domain.CarType;
import com.example.rentacar.repositories.CarTypeRepository;
import com.example.rentacar.services.ICarService;
import com.example.rentacar.repositories.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImplementation implements ICarService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CarTypeRepository carTypeRepository;

    @Override
    public List<Car> getAlLCars() {
     return carRepository.findAll();
    }

    @Override
    public Car registerCar(CarDTO carDTO) {

        try {
           // Car car=modelMapper.map(carDTO, Car.class);

            Car newCar=new Car();
            newCar.setBrand(carDTO.getBrand());
            newCar.setModel(carDTO.getModel());


            CarType carType=carTypeRepository.findById(carDTO.getCarTypeId()).orElseThrow(()-> new EntityNotFoundException("CarType not found"));

            newCar.setCarType(carType);
            return carRepository.save(newCar);
        }
        catch (Exception e) {
            throw e;
        }
    }
}
