package com.vehicle.VehicleDemo.business.repository;

import com.vehicle.VehicleDemo.business.repository.model.VehicleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleDAO, Long> {
}
