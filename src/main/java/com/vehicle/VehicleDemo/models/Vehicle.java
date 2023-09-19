package com.vehicle.VehicleDemo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Vehicle {

        private Long id;
        private String type;
        private String make;
        private String model;
        private String color;
        private int released;
        private int discontinued;

}
