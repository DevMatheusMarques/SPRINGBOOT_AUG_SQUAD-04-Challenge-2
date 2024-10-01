package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named IllegalUpdateVacancyException,
 * which is thrown when an illegal or invalid update operation is attempted on a vacancy.
 * It extends RuntimeException, meaning it is an unchecked exception.
 */
public class IllegalUpdateVacancyException extends RuntimeException {

    /**
     * Constructor for IllegalUpdateVacancyException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public IllegalUpdateVacancyException(String message) {
        super(message);
    }
}
