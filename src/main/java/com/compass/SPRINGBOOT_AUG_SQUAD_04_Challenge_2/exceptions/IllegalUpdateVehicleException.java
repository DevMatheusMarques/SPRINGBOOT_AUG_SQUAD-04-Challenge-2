package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named IllegalUpdateVehicleException,
 * which is thrown when an illegal or invalid update operation is attempted on a vehicle.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class IllegalUpdateVehicleException extends RuntimeException {

    /**
     * Constructor for IllegalUpdateVehicleException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public IllegalUpdateVehicleException(String message) {
        super(message);
    }
}
