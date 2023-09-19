package com.vehicle.VehicleDemo.business.service;

import com.vehicle.VehicleDemo.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> findAllVehicle();

    Optional<Vehicle> findVehicleById(Long id);

    Vehicle saveVehicle(Vehicle vehicle) throws Exception;
}
