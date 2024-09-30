package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidVehicleCategoryException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        String plate = vehicle.getPlate();
        Optional<Object> newVehicle = vehicleRepository.findByPlate(plate);
        if (newVehicle.isPresent()){
            throw new DataIntegrityViolationException("This vehicle already exists.");
        }
        if (vehicle.getCategory() == Category.MONTHLY_PAYER) {
            if (vehicle.getTypeVehicle() != TypeVehicle.MOTORCYCLE && vehicle.getTypeVehicle() != TypeVehicle.PASSENGER_CAR) {
                throw new InvalidVehicleCategoryException("Monthly payer vehicles can only be Motorcycle or Passenger Car");
            }
        } else if (vehicle.getCategory() == Category.DELIVERY_TRUCK && vehicle.getTypeVehicle() != TypeVehicle.DELIVERY_TRUCK) {
            throw new InvalidVehicleCategoryException("Delivery Truck vehicles must be registered as Delivery Truck category");
        } else if (vehicle.getCategory() == Category.SINGLE) {
            if (vehicle.getTypeVehicle() != TypeVehicle.MOTORCYCLE && vehicle.getTypeVehicle() != TypeVehicle.PASSENGER_CAR) {
                throw new InvalidVehicleCategoryException("Single vehicles can only be Motorcycle or Passenger Car");
            }
        } else if (vehicle.getCategory() == Category.PUBLIC_SERVICE && vehicle.getTypeVehicle() != TypeVehicle.PUBLIC_SERVICE) {
            throw new InvalidVehicleCategoryException("Public service vehicles must be registered in the public service category.");
        }
        vehicle.setRegistered(Boolean.TRUE);
        vehicle.setDateCreated(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
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
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        try {
            if (vehicle.getCategory() == Category.MONTHLY_PAYER) {
                if (vehicle.getTypeVehicle() != TypeVehicle.MOTORCYCLE && vehicle.getTypeVehicle() != TypeVehicle.PASSENGER_CAR) {
                    throw new InvalidVehicleCategoryException("Monthly payer vehicles can only be Motorcycle or Passenger Car");
                }
            } else if (vehicle.getCategory() == Category.DELIVERY_TRUCK && vehicle.getTypeVehicle() != TypeVehicle.DELIVERY_TRUCK) {
                throw new InvalidVehicleCategoryException("Delivery Truck vehicles must be registered as Delivery Truck category");
            } else if (vehicle.getCategory() == Category.SINGLE) {
                if (vehicle.getTypeVehicle() != TypeVehicle.MOTORCYCLE && vehicle.getTypeVehicle() != TypeVehicle.PASSENGER_CAR) {
                    throw new InvalidVehicleCategoryException("Single vehicles can only be Motorcycle or Passenger Car");
                }
            } else if (vehicle.getCategory() == Category.PUBLIC_SERVICE && vehicle.getTypeVehicle() != TypeVehicle.PUBLIC_SERVICE) {
                throw new InvalidVehicleCategoryException("Public service vehicles must be registered in the public service category.");
            }
            Vehicle vehicleToUpdate = findVehicleById(id);
            vehicleToUpdate.setPlate(vehicle.getPlate());
            vehicleToUpdate.setCategory(vehicle.getCategory());
            vehicleToUpdate.setTypeVehicle(vehicle.getTypeVehicle());
            vehicleToUpdate.setDateModified(LocalDateTime.now());
            return vehicleRepository.save(vehicleToUpdate);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Unable to change vehicle data");
        }
    }

    @Transactional
    public void deleteVehicle(Long id) {
        Vehicle vehicle = findVehicleById(id);
        vehicleRepository.deleteById(vehicle.getId());
    }
}
