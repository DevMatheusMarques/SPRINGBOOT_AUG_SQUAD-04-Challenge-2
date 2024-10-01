package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The VacancyRepository interface provides the necessary methods for
 * performing CRUD operations on the Vacancy entity in the database.
 * It extends JpaRepository to leverage built-in methods for data access.
 */
@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
