package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions;

/**
 * This class represents a custom exception named VehicleNotRegisteredException,
 * which is thrown when an operation is attempted on a vehicle that is not registered.
 * It extends RuntimeException, making it an unchecked exception.
 */
public class VehicleNotRegisteredException extends RuntimeException {

  /**
   * Constructor for VehicleNotRegisteredException that accepts a custom error message.
   *
   * @param message The detailed error message explaining the reason for the exception.
   */
  public VehicleNotRegisteredException(String message) {
    super(message);
  }
}
