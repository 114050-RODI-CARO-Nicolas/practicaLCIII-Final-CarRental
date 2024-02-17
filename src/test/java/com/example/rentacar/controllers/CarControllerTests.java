package com.example.rentacar.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkIfAvailableAtDate_ReturnsOk_WhenAvailable() throws Exception {

        long existingCarId = 2;
        LocalDateTime validDate = LocalDateTime.parse("2024-02-17T10:40:50.800");

        mockMvc.perform(get("/car/availability/{id}/{date}", existingCarId, validDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Available").value(true));
    }
}
