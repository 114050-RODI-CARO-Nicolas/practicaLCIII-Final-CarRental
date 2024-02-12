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
        /*TODO:
        * registerCar: Buscar como se usa el mapper de forma no conflictiva con el Car.Id y el CarType.Id
        */
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
    @Override
    public Car updateCar(long carId, CarDTO carDTO) {
        /*TODO:
        Buscar forma mas automatizada, tal vez con el ModelMapper,
        de actualizar solo las properties del Car que correspondan segun campos presentes en el DTO
        */

        try {
            Car carToUpdate=carRepository.getById(carId);
            if(carDTO.getBrand()!=null) {
                carToUpdate.setBrand(carToUpdate.getBrand());
            }
            if(carDTO.getModel()!=null){
                carToUpdate.setModel(carDTO.getModel());
            }
            if(carDTO.getCarTypeId()!=null){
                CarType newCarType=carTypeRepository.findById(carDTO.getCarTypeId()).orElseThrow(()-> new EntityNotFoundException("CarType not found"));
                carToUpdate.setCarType(newCarType);
            }


            return carRepository.save(carToUpdate);
        }
        catch (Exception e) {
            throw e;
        }


    }
}
