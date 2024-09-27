package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
public class Payment {

    private static final double MINUTE_VALUE = 0.10;
    private static final double MINIMUM_VALUE = 5;
    private Ticket ticket;

    public Double calculateValue() {
        long minutesParked = ChronoUnit.MINUTES.between(this.ticket.getDateTimeEntry(), this.ticket.getDateTimeExit());
        double valueForTime = minutesParked * MINUTE_VALUE;
        double value = valueForTime + MINIMUM_VALUE;

        return this.ticket.getVehicle().getTypeVehicle().equals(TypeVehicle.PASSENGER_CAR) ? value * 2 : value;
    }
}
