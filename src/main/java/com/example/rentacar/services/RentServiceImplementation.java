package com.example.rentacar.services;

import com.example.rentacar.DTOs.RentDTO;
import com.example.rentacar.domain.Car;
import com.example.rentacar.domain.Rent;
import com.example.rentacar.repositories.CarRepository;
import com.example.rentacar.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RentServiceImplementation implements IRentService {

    @Autowired
    RentRepository rentRepository;

    @Autowired
    CarRepository carRepository;

    @Override
    public Rent registerRent(RentDTO rentDTO) {


        Rent newRent=new Rent();
        Car rentedCar=carRepository.findById(rentDTO.getCarId()).orElse(null);
        newRent.setCar(rentedCar);
        newRent.setRentedDays(rentDTO.getRentedDays());
        newRent.setStartRent(rentDTO.getStartRent());
        newRent.setEndRent(rentDTO.getEndRent());
        newRent.setTotalPrice(rentedCar.getCarType().getPrice().multiply(   new BigDecimal(rentDTO.getRentedDays())));
        return rentRepository.save(newRent);
    }
    @Override
    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    @Override
    public void deleteRent(long id) {
        try {

            rentRepository.deleteById(id);
        }
        catch  (Exception e) {

            throw e;
        }

    }
}
