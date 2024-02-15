package com.example.rentacar.services;

import com.example.rentacar.DTOs.RentForCreationDTO;
import com.example.rentacar.DTOs.RentForUpdateDTO;
import com.example.rentacar.domain.Rent;

import java.util.List;

public interface IRentService {

    Rent registerRent(RentForCreationDTO rentForCreationDTO);
    List<Rent> getAllRents();
    Rent getRentById(long id);
    void deleteRent(long id);

    Rent updateRent(long id, RentForUpdateDTO rentForUpdateDTO);


}
