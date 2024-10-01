package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named InvalidExitCancelException,
 * which is thrown when there is an invalid exit operation related to the parking cancel.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class InvalidExitCancelException extends RuntimeException {

    /**
     * Constructor for InvalidExitCancelException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public InvalidExitCancelException(String message) {
        super(message);
    }
}
