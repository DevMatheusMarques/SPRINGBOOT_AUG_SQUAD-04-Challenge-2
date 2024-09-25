package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

public class NoResultsFoundException extends RuntimeException {
    public NoResultsFoundException(String message) {
        super(message);
    }
}
