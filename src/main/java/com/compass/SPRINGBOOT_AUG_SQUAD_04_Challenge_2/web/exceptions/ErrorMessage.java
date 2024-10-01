package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * The ErrorMessage class is a custom error response object
 * that is used to capture and return error details when exceptions occur in the application.
 */
@Getter
@ToString
public class ErrorMessage {

    /**
     * The URI path of the request that caused the error.
     */
    private String path;

    /**
     * The HTTP method (GET, POST, etc.) of the request.
     */
    private String method;

    /**
     * The HTTP status code (e.g., 400, 404, 500) of the error.
     */
    private int status;

    /**
     * The textual representation of the HTTP status (e.g., "Bad Request", "Not Found").
     */
    private String statusText;

    /**
     * The error message detailing what went wrong.
     */
    private String message;

    /**
     * A map containing field-specific errors (if applicable), such as form validation errors.
     * This field is included only when necessary (if there are validation errors).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    /**
     * Default constructor.
     */
    public ErrorMessage() {
    }

    /**
     * Constructor for building an ErrorMessage without field validation errors.
     *
     * @param request The HTTP request that triggered the error.
     * @param status  The HTTP status of the error.
     * @param message The detailed error message.
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    /**
     * Constructor for building an ErrorMessage with field validation errors.
     *
     * @param request The HTTP request that triggered the error.
     * @param status  The HTTP status of the error.
     * @param message The detailed error message.
     * @param result  The validation result that contains field errors.
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    /**
     * Adds field validation errors to the error response.
     *
     * @param result The BindingResult containing field errors from validation.
     */
    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
