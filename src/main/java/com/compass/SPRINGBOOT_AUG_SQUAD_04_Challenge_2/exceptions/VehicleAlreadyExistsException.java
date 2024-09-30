package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException {
    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
}
