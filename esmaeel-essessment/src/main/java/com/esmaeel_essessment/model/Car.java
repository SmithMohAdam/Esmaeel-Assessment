package com.esmaeel_essessment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "length")
    private double length;
    @Column(name = "weight")
    private double weight;
    @Column(name = "velocity")
    private double velocity;
    @Column(name = "color")
    private String color;

}
