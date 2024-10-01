package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named VehicleInParkingException,
 * which is thrown when an operation is attempted on a vehicle that is currently
 * parked. It extends RuntimeException, making it an unchecked exception.
 */
public class VehicleInParkingException extends RuntimeException {

    /**
     * Constructor for VehicleInParkingException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the reason for the exception.
     */
    public VehicleInParkingException(String message) {
        super(message);
    }
}
