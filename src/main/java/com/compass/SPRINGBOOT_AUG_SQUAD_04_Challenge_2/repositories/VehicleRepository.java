package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Object> findByPlate(String plate);
}
