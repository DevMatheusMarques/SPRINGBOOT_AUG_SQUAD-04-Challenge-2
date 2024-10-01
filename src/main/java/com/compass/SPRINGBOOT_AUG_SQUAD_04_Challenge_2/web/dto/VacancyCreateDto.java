package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

// Importing necessary annotations from Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The VacancyCreateDto class is a Data Transfer Object (DTO) that
 * represents the data required to create or update a vacancy.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyCreateDto {

    /**
     * The number of occupied separated spots for vehicles.
     */
    private Integer separated_occupied;

    /**
     * The total capacity of separated spots for vehicles.
     */
    private Integer separated_capacity;

    /**
     * The number of occupied monthly spots for vehicles.
     */
    private Integer monthly_occupied;

    /**
     * The total capacity of monthly spots for vehicles.
     */
    private Integer monthly_capacity;
}
