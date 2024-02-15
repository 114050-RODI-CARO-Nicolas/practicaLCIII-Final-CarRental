package com.example.rentacar.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name="Rent")
public class Rent implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_car")
    private Car car;
    private Integer rentedDays;
    private LocalDateTime startRent;
    private LocalDateTime endRent;
    private BigDecimal totalPrice;

}
