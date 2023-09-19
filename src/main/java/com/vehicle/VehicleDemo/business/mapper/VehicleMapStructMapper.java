package com.vehicle.VehicleDemo.business.mapper;

import com.vehicle.VehicleDemo.business.repository.model.VehicleDAO;
import com.vehicle.VehicleDemo.models.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VehicleMapStructMapper {
    VehicleDAO vehicleToVehicleDAO(Vehicle vehicle);
    Vehicle vehicleDAOToVehicle(VehicleDAO vehicleDAO);
}
