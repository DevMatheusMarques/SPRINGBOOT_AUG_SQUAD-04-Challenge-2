package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        try {
            return vehicleRepository.save(vehicle);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Vehicle already exists");
        }
    }

    @Transactional
    public Vehicle saveVehicleTicket(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAllVehicles() {
        try {
            return vehicleRepository.findAll();
        } catch (NoResultsFoundException e) {
            throw new NoResultsFoundException("No results found for the query.");
        }
    }

    @Transactional(readOnly = true)
    public Vehicle findVehicleById(Long id) {
        try {
            return vehicleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Vehicle not found");
        }
    }

    @Transactional(readOnly = true)
    public Vehicle findVehicleByPlate(String plate) {
        try {
            return (Vehicle) vehicleRepository.findByPlate(plate).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Vehicle not found");
        }
    }

    @Transactional(readOnly = true)
    public Vehicle findTicketVehicleByPlate(String plate) {
        return (Vehicle) vehicleRepository.findByPlate(plate).orElse(null);
    }

    @Transactional
    public Vehicle updateVehicle(Vehicle vehicle) {
        try {
            vehicle.setDateModified(LocalDateTime.now());
            return vehicleRepository.save(vehicle);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Unable to change vehicle data");
        }
    }

    @Transactional
    public void deleteVehicle(Long id) {
        try {
            vehicleRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Vehicle not found");
        }
    }
}
