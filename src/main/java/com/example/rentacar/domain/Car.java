package com.example.rentacar.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Entity
@Setter
@Getter

@Table(name="Car")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_CAR_TYPE")
    private CarType carType;




}
