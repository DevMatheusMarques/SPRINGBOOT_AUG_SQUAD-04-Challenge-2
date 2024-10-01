package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named NoResultsFoundException,
 * which is thrown when no results are found in a query or search operation.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class NoResultsFoundException extends RuntimeException {

    /**
     * Constructor for NoResultsFoundException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public NoResultsFoundException(String message) {
        super(message);
    }
}
