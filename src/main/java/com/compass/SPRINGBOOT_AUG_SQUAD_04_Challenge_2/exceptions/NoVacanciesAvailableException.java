package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

public class NoVacanciesAvailableException extends RuntimeException {
    public NoVacanciesAvailableException(String message) {
        super(message);
    }
}
