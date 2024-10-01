package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.*;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing vehicles in the system.
 * This class handles vehicle operations such as saving, finding, updating, and deleting vehicles.
 */
@RequiredArgsConstructor // Automatically generates a constructor with required dependencies.
@Service // Marks this class as a Spring service.
public class VehicleService {

    private final VehicleRepository vehicleRepository; // Repository for vehicle data access.

    /**
     * Saves a new vehicle to the database, ensuring it meets category and type constraints.
     * Throws exceptions if the vehicle already exists or if there is an invalid category/type pairing.
     *
     * @param vehicle The vehicle to save.
     * @return The saved vehicle.
     */
    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        String plate = vehicle.getPlate();
        Optional<Object> newVehicle = vehicleRepository.findByPlate(plate);
        if (newVehicle.isPresent()){
            throw new VehicleAlreadyExistsException("This vehicle already exists.");
        }
        // Validate vehicle's category and type before saving.
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

    /**
     * Saves a vehicle specifically for ticket-related operations.
     *
     * @param vehicle The vehicle to save.
     * @return The saved vehicle.
     */
    @Transactional
    public Vehicle saveVehicleTicket(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Retrieves a list of all vehicles in the system.
     *
     * @return List of vehicles.
     */
    @Transactional(readOnly = true)
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Finds a vehicle by its ID. Throws an exception if no vehicle is found.
     *
     * @param id The ID of the vehicle.
     * @return The found vehicle.
     */
    @Transactional(readOnly = true)
    public Vehicle findVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(
                () -> new NoResultsFoundException("Vehicle not found"));
    }

    /**
     * Finds a vehicle by its license plate. Throws an exception if no vehicle is found.
     *
     * @param plate The license plate of the vehicle.
     * @return The found vehicle.
     */
    @Transactional(readOnly = true)
    public Vehicle findVehicleByPlate(String plate) {
        return (Vehicle) vehicleRepository.findByPlate(plate).orElseThrow(
                () -> new NoResultsFoundException("Vehicle not found"));
    }

    /**
     * Finds a vehicle by its license plate, specifically for ticket operations. Returns null if no vehicle is found.
     *
     * @param plate The license plate of the vehicle.
     * @return The found vehicle, or null if not found.
     */
    @Transactional(readOnly = true)
    public Vehicle findTicketVehicleByPlate(String plate) {
        return (Vehicle) vehicleRepository.findByPlate(plate).orElse(null);
    }

    /**
     * Updates a vehicle's information. Validates category and type constraints before saving.
     * Throws an exception if there's an issue updating the data.
     *
     * @param id      The ID of the vehicle to update.
     * @param vehicle The new vehicle data.
     * @return The updated vehicle.
     */
    @Transactional
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        try {
            // Validate vehicle's category and type before updating.
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
            return vehicleRepository.getById(vehicleToUpdate.getId());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalUpdateVehicleException("Unable to change vehicle data");
        }
    }

    /**
     * Deletes a vehicle by its ID.
     *
     * @param id The ID of the vehicle to delete.
     */
    @Transactional
    public void deleteVehicle(Long id) {
        Vehicle vehicle = findVehicleById(id);
        vehicleRepository.deleteById(vehicle.getId());
    }
}
