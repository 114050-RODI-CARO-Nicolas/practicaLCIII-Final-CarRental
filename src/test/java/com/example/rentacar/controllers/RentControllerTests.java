package com.example.rentacar.controllers;

import com.example.rentacar.DTOs.RentForCreationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    /*

    {
      "id": 0,
      "car": "string",
      "rentedDays": 0,
      "startRent": "2024-02-17T20:50:47.516Z",
      "endRent": "2024-02-17T20:50:47.516Z",
      "totalPrice": 0
        }

     */

    @Test
    void checkIfNewRentAtValidDate_ReturnsCreated() throws Exception {
        long existingCarId=2;
        int rentedDays = 3;
        LocalDateTime validDate = LocalDateTime.parse("2024-03-07T10:00:00.000");


        RentForCreationDTO rentForCreationDTO = new RentForCreationDTO();
        rentForCreationDTO.setCarId(existingCarId);
        rentForCreationDTO.setStartRent(validDate);
        rentForCreationDTO.setRentedDays(rentedDays);

        String rentForCreationDTOJson = objectMapper.writeValueAsString(rentForCreationDTO);
        mockMvc.perform(post("/rent/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rentForCreationDTOJson))
                .andExpect(status().isCreated());

    };

    @Test
    void checkIfNewRentAtInvalidDate_ReturnsCustomException() throws Exception {

        long existingCarId=2;
        int rentedDays = 3;
        LocalDateTime invalidDate = LocalDateTime.parse("2024-02-19T16:00:00.000");

        RentForCreationDTO rentForCreationDTO = new RentForCreationDTO();
        rentForCreationDTO.setCarId(existingCarId);
        rentForCreationDTO.setStartRent(invalidDate);
        rentForCreationDTO.setRentedDays(rentedDays);

        String rentForCreationDTOJson = objectMapper.writeValueAsString(rentForCreationDTO);
        mockMvc.perform(post("/rent/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rentForCreationDTOJson))
                .andExpect(status().isConflict());

    }





}
