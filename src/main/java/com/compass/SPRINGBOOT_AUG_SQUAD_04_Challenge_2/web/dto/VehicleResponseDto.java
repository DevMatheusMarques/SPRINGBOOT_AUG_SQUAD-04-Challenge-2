package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Vehicle responses.
 * This class is used to return vehicle information in API responses.
 */
@AllArgsConstructor
@Getter
@Setter
public class VehicleResponseDto {

    /**
     * The unique identifier of the vehicle.
     */
    private long id;

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

    /**
     * A boolean indicating if the vehicle is registered.
     */
    private boolean registered;
}
