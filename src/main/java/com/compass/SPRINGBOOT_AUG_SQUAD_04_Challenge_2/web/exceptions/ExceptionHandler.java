package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoVacanciesAvailableException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoResultsFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoVacanciesAvailableException.class)
    public ResponseEntity<ErrorMessage> conflictException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }

}
