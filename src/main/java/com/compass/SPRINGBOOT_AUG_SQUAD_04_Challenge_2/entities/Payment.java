package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import lombok.AllArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
public class Payment {

    private static final double MINUTE_VALUE = 0.10;
    private static final double MINIMUM_VALUE = 5;
    private Ticket ticket;

    public Double calculateValue() {
        long minutesParked = Duration.between(this.ticket.getDateTimeEntry(), this.ticket.getDateTimeExit()).toMinutes();
        double valueForTime = minutesParked * MINUTE_VALUE;
        double value = valueForTime + MINIMUM_VALUE;

        return this.ticket.getVehicle().getType().equals(TypeVehicle.PASSENGER_CAR) ? value * 2 : value;
    }
}
