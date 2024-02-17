package com.example.rentacar.services;

import com.example.rentacar.DTOs.RentForCreationDTO;
import com.example.rentacar.DTOs.RentForUpdateDTO;
import com.example.rentacar.domain.Car;
import com.example.rentacar.domain.Rent;
import com.example.rentacar.exceptions.CurrentlyRentedCarException;
import com.example.rentacar.exceptions.RentedAtParticularDatePeriodException;
import com.example.rentacar.repositories.CarRepository;
import com.example.rentacar.repositories.RentRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Car carToRent=carRepository.findById(rentForCreationDTO.getCarId()).orElseThrow( ()-> new EntityNotFoundException("Source: RegisterRent() - The requested car does not exist."));

        List<Rent> existingRents = carToRent.getRentList();

        newRent.setCar(carToRent);
        newRent.setRentedDays(rentForCreationDTO.getRentedDays());
        if(rentForCreationDTO.getStartRent()==null)
        {
            newRent.setStartRent(currentDateTime);
           // newRent.setEndRent(newRent.getStartRent().plusDays(newRent.getRentedDays()));
            newRent.setEndRent(currentDateTime.plusDays(rentForCreationDTO.getRentedDays()));
        }
        else
        {
            newRent.setStartRent(rentForCreationDTO.getStartRent());
            //newRent.setEndRent(newRent.getEndRent().plusDays(newRent.getRentedDays()));
            newRent.setEndRent(rentForCreationDTO.getStartRent().plusDays(rentForCreationDTO.getRentedDays()));
        }

        if ( ProcessUtils.CheckIfRequestedRentPeriodCollidesWithExistingRents(existingRents, newRent.getStartRent(), newRent.getEndRent()))
        {
            throw new RentedAtParticularDatePeriodException("Source: RegisterRent() - The car is under rental at the requested date.");
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
      return rentRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Source: GetRentById() - The requested rent could not be found"));
    }

    @Override
    public void deleteRent(long id) {
        try {

            Rent rentToDelete = rentRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("DeleteRent() - No rent found with the requested id") );
            rentRepository.deleteById(id);
        }
        catch  (Exception e) {

            throw e;
        }

    }

    @Override
    public Rent updateRent(long id, RentForUpdateDTO rentForUpdateDTO) {
          /*
        -Se puede actualizar el int rentedDays y la fecha startRent, y en base a eso se calcula la endRent.
        -Si se envia la startRent, se toma esa nueva fecha como inicio de renta.
        -Si no se envia la startRent, se toma como fecha de inicio la fecha de sistema.
         */

        LocalDateTime currentDateTime = LocalDateTime.now();

        Rent rentToUpdate=rentRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Source: UpdateRent() - The requested rent was not found") );
        Car carToRent=carRepository.findById(rentToUpdate.getCar().getId()).orElseThrow(()-> new EntityNotFoundException("Source: UpdateRent() - The car associated to the requested rent could not be found")) ;

        List<Rent> existingRents = carToRent.getRentList();
        rentToUpdate.setRentedDays(rentForUpdateDTO.getRentedDays());

        if(rentForUpdateDTO.getStartRent()==null) {

            LocalDateTime CalculatedSystemLocalDateTimeEndRent = currentDateTime.plusDays(rentToUpdate.getRentedDays());

            if ( ProcessUtils.CheckIfRequestedRentPeriodCollidesWithExistingRents(existingRents, currentDateTime, CalculatedSystemLocalDateTimeEndRent) ) {
                throw new RentedAtParticularDatePeriodException("Source: UpdateRent() - The car is under rental at the requested date.");
            }
            rentToUpdate.setStartRent(currentDateTime);
            rentToUpdate.setEndRent(CalculatedSystemLocalDateTimeEndRent);
        }
        else
        {
            LocalDateTime DTOCalculatedEndRent = rentForUpdateDTO.getStartRent().plusDays(rentToUpdate.getRentedDays());

            if ( ProcessUtils.CheckIfRequestedRentPeriodCollidesWithExistingRents(existingRents, rentForUpdateDTO.getStartRent(), DTOCalculatedEndRent ) ) {
                throw new RentedAtParticularDatePeriodException("Source: UpdateRent() - The car is under rental at the requested date.");
            }
            rentToUpdate.setStartRent(rentForUpdateDTO.getStartRent());
            rentToUpdate.setEndRent(DTOCalculatedEndRent);

        }


        rentToUpdate.setTotalPrice(rentToUpdate.getCar().getCarType().getPrice().multiply(new BigDecimal(rentToUpdate.getRentedDays())));

        return rentRepository.save(rentToUpdate);


    }
}


