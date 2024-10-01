package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The ExceptionHandler class provides centralized exception handling for all exceptions that occur within the application.
 * It captures specific exceptions and maps them to appropriate HTTP response statuses and error messages, ensuring consistency in error handling.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * Handles exceptions that indicate a bad request (HTTP 400), such as validation errors, vehicle-related exceptions, or invalid operations.
     *
     * @param e       The runtime exception that occurred.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorMessage object and a HTTP status code of 400.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class, VehicleNotRegisteredException.class, InvalidVehicleCategoryException.class,
            IllegalUpdateVehicleException.class, InvalidEntryCancelException.class, InvalidExitCancelException.class, UnauthorizedCancelAccessException.class})
    public ResponseEntity<ErrorMessage> BadRequestException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    /**
     * Handles exceptions where a requested resource or entity is not found (HTTP 404).
     *
     * @param e       The runtime exception indicating a missing resource.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorMessage object and a HTTP status code of 404.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({NoResultsFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> NotFoundException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Handles exceptions that indicate a conflict (HTTP 409), such as when a vehicle already exists in the parking system or when there are no vacancies available.
     *
     * @param e       The runtime exception indicating a conflict.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorMessage object and a HTTP status code of 409.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({VehicleInParkingException.class, NoVacanciesAvailableException.class, IllegalUpdateVacancyException.class,
            VehicleAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> ConflictException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }

    /**
     * Handles exceptions that result in an unprocessable entity response (HTTP 422), such as invalid state transitions or illegal arguments.
     *
     * @param e       The runtime exception that caused the error.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorMessage object and a HTTP status code of 422.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> UnprocessableEntityException(RuntimeException e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    /**
     * Handles all other exceptions that are not explicitly captured by more specific handlers, resulting in an internal server error (HTTP 500).
     *
     * @param e       The general exception that occurred.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorMessage object and a HTTP status code of 500.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> StandardException(Exception e, HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
