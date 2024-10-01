package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named UnauthorizedCancelAccessException,
 * which is thrown when an unauthorized attempt is made to access or perform
 * an operation related to canceling.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class UnauthorizedCancelAccessException extends RuntimeException {

    /**
     * Constructor for UnauthorizedCancelAccessException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public UnauthorizedCancelAccessException(String message) {
        super(message);
    }
}
