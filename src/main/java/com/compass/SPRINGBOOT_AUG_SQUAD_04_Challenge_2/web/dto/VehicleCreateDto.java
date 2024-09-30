package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new Vehicle.
 * This class holds the necessary information to create a vehicle entity,
 * typically used when receiving data from API requests.
 */
@AllArgsConstructor
@Getter
@Setter
public class VehicleCreateDto {

    /**
     * The license plate of the vehicle.
     */
    private String plate;

    /**
     * The type of the vehicle (e.g., MOTORCYCLE, PASSENGER_CAR).
     */
    private String typeVehicle;

    /**
     * The category of the vehicle (e.g., MONTHLY_PAYER, SINGLE).
     */
    private String category;
}
