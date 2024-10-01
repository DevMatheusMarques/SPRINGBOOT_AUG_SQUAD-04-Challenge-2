package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named InvalidVehicleCategoryException,
 * which is thrown when the category of a vehicle is invalid or does not meet the required conditions.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class InvalidVehicleCategoryException extends RuntimeException {

  /**
   * Constructor for InvalidVehicleCategoryException that accepts a custom error message.
   *
   * @param message The detailed error message explaining the cause of the exception.
   */
  public InvalidVehicleCategoryException(String message) {
    super(message);
  }
}
