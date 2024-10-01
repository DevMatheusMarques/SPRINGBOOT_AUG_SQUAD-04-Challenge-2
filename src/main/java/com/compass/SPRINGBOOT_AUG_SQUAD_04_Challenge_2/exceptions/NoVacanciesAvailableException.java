package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named NoVacanciesAvailableException,
 * which is thrown when there are no available vacancies (parking spots or similar)
 * during an operation.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class NoVacanciesAvailableException extends RuntimeException {

    /**
     * Constructor for NoVacanciesAvailableException that accepts a custom error message.
     *
     * @param message The detailed error message explaining the cause of the exception.
     */
    public NoVacanciesAvailableException(String message) {
        super(message);
    }
}
