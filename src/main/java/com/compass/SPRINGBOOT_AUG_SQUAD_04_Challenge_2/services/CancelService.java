package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import org.springframework.stereotype.Service;

@Service
public class CancelService {

    public static boolean allowEntry(TypeVehicle typeVehicle, Integer cancel) {
        try {
            boolean isAllowed = false;
            // If the cancel is greater than or equal to 6, it's not an entry cancel
            if (cancel >= 6) {
                System.out.println("Vehicles cannot enter through this cancel. Please use cancels 1, 2, 3, 4, or 5.");
                return isAllowed;
            }

            // Check the type of vehicle passed
            switch (typeVehicle) {
                case PASSENGER_CAR, PUBLIC_SERVICE:
                    // Passenger cars and Public Service vehicles can enter through any entry cancel
                    isAllowed = openCancel();
                    break;

                case MOTOCYCLE:
                    // Motorcycles can only enter through cancel 5
                    if (cancel == 5) {
                        isAllowed = openCancel();
                    } else {
                        System.out.println("This type of vehicle cannot enter through this cancel. Please use cancel 5.");
                    }
                    break;

                case DELIVERY_TRUCK:
                    // Delivery trucks can only enter through cancel 1
                    if (cancel == 1) {
                        isAllowed = openCancel();
                    } else {
                        System.out.println("This type of vehicle cannot enter through this cancel. Please use cancel 1.");
                    }
                    break;
            }
            return isAllowed;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean allowExit(TypeVehicle typeVehicle, Integer cancel) {
        try {
            boolean isAllowed = false;
            // If the cancel is less than or equal to 5, it's not an exit cancel
            if (cancel <= 5) {
                System.out.println("Vehicles cannot exit through this cancel. Please use cancels 6, 7, 8, 9, or 10.");
                return isAllowed;
            }

            // Check the type of vehicle passed
            switch (typeVehicle) {
                case PASSENGER_CAR, PUBLIC_SERVICE, DELIVERY_TRUCK:
                    // Passenger cars, Public Service, and Delivery trucks can exit through any exit cancel
                    isAllowed = openCancel();
                    break;

                case MOTOCYCLE:
                    // Motorcycles can only exit through cancel 10
                    if (cancel == 10) {
                        isAllowed = openCancel();
                    } else {
                        System.out.println("This type of vehicle cannot exit through this cancel. Please use cancel 10.");
                    }
                    break;
            }
            return isAllowed;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean openCancel() {
        // Simulate opening the cancel
        System.out.println("Cancel opened.");
        return true;
    }
}
