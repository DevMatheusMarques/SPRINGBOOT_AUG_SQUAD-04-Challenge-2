package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidEntryCancelException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidExitCancelException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.UnauthorizedCancelAccessException;
import org.springframework.stereotype.Service;

/**
 * This class provides services for managing vehicle entry and exit through cancels
 * in a parking system. It determines whether a vehicle of a specific type
 * is allowed to enter or exit through a designated cancel.
 */
@Service
public class CancelService {

    /**
     * Determines if a vehicle is allowed to enter through the specified cancel.
     *
     * @param typeVehicle The type of the vehicle attempting to enter.
     * @param cancel      The cancel number through which the vehicle is attempting to enter.
     * @return true if the vehicle is allowed to enter, false otherwise.
     * @throws InvalidEntryCancelException     if the cancel number is not valid for entry.
     * @throws UnauthorizedCancelAccessException if the vehicle type is not authorized to enter through the specified cancel.
     */
    public static boolean allowEntry(TypeVehicle typeVehicle, Integer cancel) {
        // If the cancel is greater than or equal to 6, it's not an entry cancel
        if (cancel >= 6) {
            throw new InvalidEntryCancelException("Vehicles cannot enter through this cancel. Please use cancels 1, 2, 3, 4, or 5.");
        }

        // Check the type of vehicle passed
        switch (typeVehicle) {
            case PASSENGER_CAR, PUBLIC_SERVICE:
                // Passenger cars and Public Service vehicles can enter through any entry cancel
                return openCancel();

            case MOTORCYCLE:
                // Motorcycles can only enter through cancel 5
                if (cancel == 5) {
                    return openCancel();
                } else {
                    throw new UnauthorizedCancelAccessException("This type of vehicle cannot enter through this cancel. Please use cancel 5.");
                }

            case DELIVERY_TRUCK:
                // Delivery trucks can only enter through cancel 1
                if (cancel == 1) {
                    return openCancel();
                } else {
                    throw new UnauthorizedCancelAccessException("This type of vehicle cannot enter through this cancel. Please use cancel 1.");
                }

            default:
                throw new UnauthorizedCancelAccessException("Unknown vehicle type.");
        }
    }

    /**
     * Determines if a vehicle is allowed to exit through the specified cancel.
     *
     * @param typeVehicle The type of the vehicle attempting to exit.
     * @param cancel      The cancel number through which the vehicle is attempting to exit.
     * @return true if the vehicle is allowed to exit, false otherwise.
     * @throws InvalidExitCancelException      if the cancel number is not valid for exit.
     * @throws UnauthorizedCancelAccessException if the vehicle type is not authorized to exit through the specified cancel.
     */
    public static boolean allowExit(TypeVehicle typeVehicle, Integer cancel) {
        // If the cancel is less than or equal to 5, it's not an exit cancel
        if (cancel < 6 || cancel > 10) {
            throw new InvalidExitCancelException("Vehicles cannot exit through this cancel. Please use cancels 6, 7, 8, 9, or 10.");
        }

        // Check the type of vehicle passed
        switch (typeVehicle) {
            case PASSENGER_CAR, PUBLIC_SERVICE, DELIVERY_TRUCK:
                // Passenger cars, Public Service, and Delivery trucks can exit through any exit cancel
                return openCancel();

            case MOTORCYCLE:
                // Motorcycles can only exit through cancel 10
                if (cancel == 10) {
                    return openCancel();
                } else {
                    throw new UnauthorizedCancelAccessException("This type of vehicle cannot exit through this cancel. Please use cancel 10.");
                }

            default:
                throw new UnauthorizedCancelAccessException("Unknown vehicle type.");
        }
    }

    /**
     * Simulates the action of opening a cancel.
     *
     * @return true indicating the cancel has been opened.
     */
    public static boolean openCancel() {
        // Simulate opening the cancel
        return true;
    }
}
