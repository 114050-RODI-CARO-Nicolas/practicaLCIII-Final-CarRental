package com.example.rentacar.controllers;


import com.example.rentacar.DTOs.RentForCreationDTO;
import com.example.rentacar.DTOs.RentForUpdateDTO;
import com.example.rentacar.DTOs.RentResponseDTO;
import com.example.rentacar.domain.Rent;
import com.example.rentacar.services.RentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    RentServiceImplementation rentServiceImp;

    @GetMapping("/all")
    public ResponseEntity<List<RentResponseDTO>> getAllRents(){

        /*
        TODO: Aplicar Mapper
         */

        List<Rent> rents = rentServiceImp.getAllRents();
        List<RentResponseDTO> rentResponseDTOList= new ArrayList<RentResponseDTO>();

        for (Rent rent:rents)
        {

            RentResponseDTO rentResponseDTO=new RentResponseDTO();
            rentResponseDTO.setRentId(rent.getId());
            rentResponseDTO.setBrand(rent.getCar().getBrand());
            rentResponseDTO.setModel(rent.getCar().getModel());
            rentResponseDTO.setRentedDays(rent.getRentedDays());
            rentResponseDTO.setStartRent(rent.getStartRent());
            rentResponseDTO.setEndRent(rent.getEndRent());
            rentResponseDTO.setTotalPrice(rent.getTotalPrice());
            rentResponseDTOList.add(rentResponseDTO);
        }

        return ResponseEntity.ok(rentResponseDTOList);
    }

    @PostMapping("/new")
    public ResponseEntity<Rent> registerRent (@RequestBody RentForCreationDTO rentForCreationDTO){
        Rent newRent= rentServiceImp.registerRent(rentForCreationDTO);
        return new ResponseEntity<>(newRent, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, String>> deleteRent (@PathVariable long id)
    {
        HashMap<String, String> deleteStatus=new HashMap<>();
        try {
            rentServiceImp.deleteRent(id);
            deleteStatus.put("Success", "true");
            return ResponseEntity.ok(deleteStatus);
        }
        catch (Exception e) {
            deleteStatus.put("Message", e.getMessage());
            return ResponseEntity.internalServerError().body(deleteStatus);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentResponseDTO> getRentInfo(@PathVariable long id)
    {
         /*
            TODO: Aplicar Mapper
         */

        Rent foundRent = rentServiceImp.getRentById(id);
        RentResponseDTO rentResponseDTO = new RentResponseDTO();
        rentResponseDTO.setRentId(foundRent.getId());
        rentResponseDTO.setBrand(foundRent.getCar().getBrand());
        rentResponseDTO.setModel(foundRent.getCar().getModel());
        rentResponseDTO.setRentedDays(foundRent.getRentedDays());
        rentResponseDTO.setStartRent(foundRent.getStartRent());
        rentResponseDTO.setEndRent(foundRent.getEndRent());
        rentResponseDTO.setTotalPrice(foundRent.getTotalPrice());

        return ResponseEntity.ok(rentResponseDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Rent> updateRent(@PathVariable long id, @RequestBody RentForUpdateDTO rentForUpdateDTO){

        Rent updatedRent = rentServiceImp.updateRent(id, rentForUpdateDTO);
        return new ResponseEntity<>(updatedRent, HttpStatus.OK);
    }





}
