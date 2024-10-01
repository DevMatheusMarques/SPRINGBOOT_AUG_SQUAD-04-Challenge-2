package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The VacancyResponseDto class is a Data Transfer Object (DTO)
 * that represents the response data for a vacancy.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyResponseDto {

    /**
     * The unique identifier of the vacancy.
     */
    private Long id;

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
