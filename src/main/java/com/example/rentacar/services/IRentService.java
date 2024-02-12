package com.example.rentacar.services;

import com.example.rentacar.DTOs.RentDTO;
import com.example.rentacar.domain.Rent;

import java.util.List;

public interface IRentService {

    Rent registerRent(RentDTO rentDTO);
    List<Rent> getAllRents();


}
