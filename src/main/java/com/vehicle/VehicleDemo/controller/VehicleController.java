package com.vehicle.VehicleDemo.controller;

import com.vehicle.VehicleDemo.business.service.VehicleService;
import com.vehicle.VehicleDemo.models.Vehicle;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Log4j2
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> findAllVehicle() {
        log.info("Retrieve list of vehicle");
        List<Vehicle> vehicleList = vehicleService.findAllVehicle();
        if (vehicleList.isEmpty()) {
            log.warn("Vehicle list is empty! {}", vehicleList);
            return ResponseEntity.notFound().build();
        }
        log.debug("Vehicle list is found. Size: {}", vehicleList::size);
        return ResponseEntity.ok(vehicleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@NonNull @PathVariable(name = "id") Long id) {
        log.info("Find vehicle by passing ID, where vehicle ID is :{} ", id);
        Optional<Vehicle> vehicle = (vehicleService.findVehicleById(id));
        if (!vehicle.isPresent()) {
            log.warn("Vehicle with id {} is not found.", id);
        } else {
            log.debug("Vehicle with id {} is found: {}", id, vehicle);
        }
        return vehicle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody Vehicle vehicle, BindingResult bindingResult) throws Exception {
        log.info("Create new vehicle by passing : {}", vehicle);
        if (bindingResult.hasErrors()) {
            log.error("New vehicle is not created: error {}", bindingResult);
            return ResponseEntity.badRequest().build();
        }
        Vehicle vehicleSaved = vehicleService.saveVehicle(vehicle);
        log.debug("New vehicle is created: {}", vehicle);
        return new ResponseEntity<>(vehicleSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteVehicleById(@NonNull @PathVariable Long id) {
        log.info("Delete Vehicle by passing ID, where ID is:{}", id);
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(id);
        if (!(vehicle.isPresent())) {
            log.warn("Vehicle for delete with id {} is not found.", id);
            return ResponseEntity.notFound().build();
        }
        vehicleService.deleteVehicleById(id);
        log.debug("Vehicle with id {} is deleted: {}", id, vehicle);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
