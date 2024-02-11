package com.example.rentacar.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;



@Entity
@Setter
@Getter
@Table(name="CarType")
public class CarType implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String type;
    private BigDecimal price;





}
