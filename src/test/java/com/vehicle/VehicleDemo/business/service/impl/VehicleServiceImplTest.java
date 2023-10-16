package com.vehicle.VehicleDemo.business.service.impl;

import com.vehicle.VehicleDemo.business.mapper.VehicleMapStructMapper;
import com.vehicle.VehicleDemo.business.repository.VehicleRepository;
import com.vehicle.VehicleDemo.business.repository.model.VehicleDAO;
import com.vehicle.VehicleDemo.models.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleServiceImplTest {
    @Mock
    private VehicleRepository repository;
    @InjectMocks
    private VehicleServiceImpl service;

    @Mock
    private VehicleMapStructMapper mapper;

    private Vehicle vehicle;
    private VehicleDAO vehicleDAO;
    private List<Vehicle> vehicleList;
    private List<VehicleDAO> vehicleDAOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllVehicle_successful() {
        vehicleDAOList = createVehicleDAOList();
        vehicleList = createVehicleList();

        when(repository.findAll()).thenReturn(vehicleDAOList);
        List<Vehicle> vehicles = service.findAllVehicle();
        assertEquals(2, vehicles.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAllVehicle_invalid() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertTrue(service.findAllVehicle().isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findVehicleById_successful() {
        vehicleDAO = createVehicleDAO(1L);
        vehicle = createVehicle(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(vehicleDAO));
        when(mapper.vehicleDAOToVehicle(vehicleDAO)).thenReturn(vehicle);
        Optional<Vehicle> returnedVehicle = service.findVehicleById(vehicle.getId());
        assertEquals(vehicle.getId(), returnedVehicle.get().getId());
        assertEquals(vehicle.getType(), returnedVehicle.get().getType());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void findVehicleById_invalid() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertFalse(service.findVehicleById(anyLong()).isPresent());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void deleteVehicleById_successful() {
        service.deleteVehicleById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteVehicleById_invalid() {
        doThrow(new IllegalArgumentException()).when(repository).deleteById(anyLong());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.deleteVehicleById(anyLong()));
    }

    @Test
    void saveVehicle_successful() throws Exception {
        when(repository.save(vehicleDAO)).thenReturn(vehicleDAO);
        when(mapper.vehicleDAOToVehicle(vehicleDAO)).thenReturn(vehicle);
        when(mapper.vehicleToVehicleDAO(vehicle)).thenReturn(vehicleDAO);
        Vehicle vehicleSaved = service.saveVehicle(vehicle);
        assertEquals(vehicle, vehicleSaved);
        verify(repository, times(1)).save(vehicleDAO);
    }

    @Test
    void saveVehicle_invalid() {
        when(repository.save(vehicleDAO)).thenThrow(new IllegalArgumentException());
        when(mapper.vehicleToVehicleDAO(vehicle)).thenReturn(vehicleDAO);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveVehicle(vehicle));
        verify(repository, times(1)).save(vehicleDAO);
    }


    private Vehicle createVehicle(Long id){
        Vehicle vehicle =new Vehicle();
        vehicle.setId(id);
        vehicle.setType("testType");
        vehicle.setMake("testMake");
        vehicle.setModel("testModel");
        vehicle.setColor("testColor");
        vehicle.setReleased(2000);
        vehicle.setDiscontinued(2005);
        return vehicle;
    }
    private VehicleDAO createVehicleDAO(Long id){
        VehicleDAO vehicleDAO =new VehicleDAO();
        vehicleDAO.setId(id);
        vehicleDAO.setType("testType");
        vehicleDAO.setMake("testMake");
        vehicleDAO.setModel("testModel");
        vehicleDAO.setColor("testColor");
        vehicleDAO.setReleased(2000);
        vehicleDAO.setDiscontinued(2005);
        return vehicleDAO;
    }
    private List<Vehicle> createVehicleList() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(createVehicle(1L));
        vehicleList.add(createVehicle(2L));
        return vehicleList;
    }

    private List<VehicleDAO> createVehicleDAOList() {
        List<VehicleDAO> vehicleDAOList = new ArrayList<>();
        vehicleDAOList.add(createVehicleDAO(1L));
        vehicleDAOList.add(createVehicleDAO(2L));
        return vehicleDAOList;
    }

}
