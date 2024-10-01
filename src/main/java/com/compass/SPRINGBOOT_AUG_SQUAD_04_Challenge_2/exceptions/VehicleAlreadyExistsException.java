package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named VehicleAlreadyExistsException,
 * which is thrown when there is an attempt to create a vehicle that already exists
 * in the system. It extends RuntimeException, making it an unchecked exception.
 */
public class VehicleAlreadyExistsException extends RuntimeException {

    /**
     * Constructor for VehicleAlreadyExistsException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the reason for the exception.
     */
    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
}
