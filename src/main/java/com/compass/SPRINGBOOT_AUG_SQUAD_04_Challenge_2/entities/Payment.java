package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * The Payment class is responsible for calculating the parking fee for a given ticket.
 * It considers the duration of parking and the type of vehicle to compute the final cost.
 */
@AllArgsConstructor
public class Payment {

    // Constant value representing the cost per minute
    private static final double MINUTE_VALUE = 0.10;

    // Constant value representing the minimum charge for parking
    private static final double MINIMUM_VALUE = 5;

    // The ticket associated with the payment
    private Ticket ticket;

    /**
     * Calculates the total value to be charged for parking based on the duration
     * the vehicle has been parked and its type.
     *
     * @return the calculated parking fee as a Double
     */
    public Double calculateValue() {
        // Calculate the total number of minutes parked
        long minutesParked = ChronoUnit.MINUTES.between(this.ticket.getDateTimeEntry(), this.ticket.getDateTimeExit());

        // Calculate the value based on time parked
        double valueForTime = minutesParked * MINUTE_VALUE;

        // Calculate the total value, including the minimum charge
        double value = valueForTime + MINIMUM_VALUE;

        // If the vehicle is a passenger car, double the value
        return this.ticket.getVehicle().getTypeVehicle().equals(TypeVehicle.PASSENGER_CAR) ? value * 2 : value;
    }
}
