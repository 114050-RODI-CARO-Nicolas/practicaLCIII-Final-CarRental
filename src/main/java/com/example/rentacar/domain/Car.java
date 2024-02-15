package com.example.rentacar.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;


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

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_CAR_TYPE")
    private CarType carType;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rent> rentList;




}
