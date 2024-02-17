package com.example.rentacar.services;

import com.example.rentacar.DTOs.RentForCreationDTO;
import com.example.rentacar.DTOs.RentForUpdateDTO;
import com.example.rentacar.domain.Car;
import com.example.rentacar.domain.Rent;
import com.example.rentacar.exceptions.CurrentlyRentedCarException;
import com.example.rentacar.exceptions.RentedAtParticularDatePeriodException;
import com.example.rentacar.repositories.CarRepository;
import com.example.rentacar.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.example.rentacar.services.utils.ProcessUtils;

@Service

public class RentServiceImplementation implements IRentService {

    @Autowired
    RentRepository rentRepository;

    @Autowired
    CarRepository carRepository;



    @Override
    public Rent registerRent(RentForCreationDTO rentForCreationDTO) {
        /*
            Si no se envia startRent, se registra como fecha de inicio de renta la fecha actual de sistema.
         */

        LocalDateTime currentDateTime = LocalDateTime.now();

        Rent newRent=new Rent();
        Car carToRent=carRepository.findById(rentForCreationDTO.getCarId()).orElse(null);

        List<Rent> existingRents = carToRent.getRentList();




        newRent.setCar(carToRent);
        newRent.setRentedDays(rentForCreationDTO.getRentedDays());
        if(rentForCreationDTO.getStartRent()==null)
        {
            newRent.setStartRent(currentDateTime);
           // newRent.setEndRent(newRent.getStartRent().plusDays(newRent.getRentedDays()));
            newRent.setEndRent(currentDateTime.plusDays(rentForCreationDTO.getRentedDays()));
        } else {
            newRent.setStartRent(rentForCreationDTO.getStartRent());
            //newRent.setEndRent(newRent.getEndRent().plusDays(newRent.getRentedDays()));
            newRent.setEndRent(rentForCreationDTO.getStartRent().plusDays(rentForCreationDTO.getRentedDays()));
        }

        if ( ProcessUtils.checkIfRentedAtParticularDate(existingRents, newRent.getStartRent()) ) {
            throw new RentedAtParticularDatePeriodException("The car is under rental at the requested date.");
        }

        newRent.setTotalPrice(carToRent.getCarType().getPrice().multiply(   new BigDecimal(rentForCreationDTO.getRentedDays())));
        return rentRepository.save(newRent);
    }
    @Override
    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    @Override
    public Rent getRentById(long id) {
      return rentRepository.findById(id).orElse(null);
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

    @Override
    public Rent updateRent(long id, RentForUpdateDTO rentForUpdateDTO) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        /*
        -Se puede actualizar el int rentedDays y la fecha startRent, y en base a eso se calcula la endRent.
        -Si se envia la startRent, se toma esa nueva fecha como inicio de renta.
        -Si no se envia la startRent, se toma como fecha de inicio la fecha de sistema.
         */
        Rent rentToUpdate=rentRepository.findById(id).orElse(null);
        Car carToRent=carRepository.findById(rentToUpdate.getCar().getId()).orElse(null);

        List<Rent> existingRents = carToRent.getRentList();

        /*
        if(rentForUpdateDTO.getCarId()!=null){
            Car newCar = carRepository.findById(rentForUpdateDTO.getCarId()).orElse(null);
            rentToUpdate.setCar(newCar);
        } */

        rentToUpdate.setRentedDays(rentForUpdateDTO.getRentedDays());
        if(rentForUpdateDTO.getStartRent()==null) {
            if ( ProcessUtils.checkIfRentedAtParticularDate(existingRents, currentDateTime) ) {
                throw new RentedAtParticularDatePeriodException("The car is under rental at the requested date.");
            }
            rentToUpdate.setStartRent(currentDateTime);
            rentToUpdate.setEndRent(currentDateTime.plusDays(rentForUpdateDTO.getRentedDays()));
        } else {
            if ( ProcessUtils.checkIfRentedAtParticularDate(existingRents, rentForUpdateDTO.getStartRent()) ) {
                throw new RentedAtParticularDatePeriodException("The car is under rental at the requested date.");
            }
            rentToUpdate.setStartRent(rentForUpdateDTO.getStartRent());
            rentToUpdate.setEndRent(rentForUpdateDTO.getStartRent().plusDays(rentForUpdateDTO.getRentedDays()));
        }


        rentToUpdate.setTotalPrice(rentToUpdate.getCar().getCarType().getPrice().multiply(new BigDecimal(rentToUpdate.getRentedDays())));

        return rentRepository.save(rentToUpdate);


    }
}


