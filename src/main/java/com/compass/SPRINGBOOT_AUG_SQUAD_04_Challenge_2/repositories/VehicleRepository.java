package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and manipulating Vehicle entities in the database.
 * It extends JpaRepository to provide built-in CRUD (Create, Read, Update, Delete) operations.
 * This interface manages Vehicle entities identified by a Long type ID.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * Finds a vehicle by its license plate.
     *
     * @param plate The license plate of the vehicle to search for.
     * @return An Optional containing the found vehicle, or an empty Optional if no vehicle is found.
     */
    Optional<Object> findByPlate(String plate);
}
