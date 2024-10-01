package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named InvalidEntryCancelException,
 * which is thrown when there is an invalid entry operation related to the parking cancel.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class InvalidEntryCancelException extends RuntimeException {

    /**
     * Constructor for InvalidEntryCancelException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public InvalidEntryCancelException(String message) {
        super(message);
    }
}
