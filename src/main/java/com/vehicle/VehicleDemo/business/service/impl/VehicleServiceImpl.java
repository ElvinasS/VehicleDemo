package com.vehicle.VehicleDemo.business.service.impl;

import com.vehicle.VehicleDemo.business.mapper.VehicleMapStructMapper;
import com.vehicle.VehicleDemo.business.repository.VehicleRepository;
import com.vehicle.VehicleDemo.business.repository.model.VehicleDAO;
import com.vehicle.VehicleDemo.business.service.VehicleService;
import com.vehicle.VehicleDemo.models.Vehicle;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    VehicleMapStructMapper vehicleMapStructMapper;

    @Override
    public List<Vehicle> findAllVehicle() {
        List<VehicleDAO> vehicleDAOList = vehicleRepository.findAll();
        log.info("Get training list. Size is: {}", vehicleDAOList::size);
        return vehicleDAOList.stream().map(vehicleMapStructMapper::vehicleDAOToVehicle).collect(Collectors.toList());
    }

    @Override
    public Optional<Vehicle> findVehicleById(Long id) {
        Optional<Vehicle> vehicleById = vehicleRepository.findById(id)
                .flatMap(vehicle -> Optional.ofNullable(vehicleMapStructMapper.vehicleDAOToVehicle(vehicle)));
        log.info("Vehicle with id {} is {}", id, vehicleById);
        return vehicleById;
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) throws Exception {
        if (!hasMatch(vehicle)) {
            VehicleDAO vehicleSaved = vehicleRepository.save(vehicleMapStructMapper.vehicleToVehicleDAO(vehicle));
            log.info("New vehicle saved: {}", () -> vehicleSaved);
            return vehicleMapStructMapper.vehicleDAOToVehicle(vehicleSaved);
        }else {
            log.error("Vehicle conflict exception is thrown: {}", HttpStatus.CONFLICT);
            throw new HttpClientErrorException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public void deleteVehicleById(Long id) {
        vehicleRepository.deleteById(id);
        log.info("Vehicle with id {} is deleted", id);
    }

    public boolean hasMatch(Vehicle vehicle){
        return vehicleRepository.findAll().stream().anyMatch(vehicleDAO -> isSame(vehicle, vehicleDAO));
    }

    private boolean isSame(Vehicle vehicle, VehicleDAO vehicleDAO) {
        return vehicleDAO.getId().equals(vehicle.getId()) &&
                vehicleDAO.getModel().equals(vehicle.getModel()) ;
    }
}
