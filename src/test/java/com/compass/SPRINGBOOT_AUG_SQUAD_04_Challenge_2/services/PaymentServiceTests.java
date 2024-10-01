package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Payment;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTests {

    private Payment payment;
    private Ticket ticket;

    @BeforeEach
    public void init() {
        ticket = mock(Ticket.class);

        when(ticket.getDateTimeEntry()).thenReturn(LocalDateTime.of(2024, 10, 1, 10, 0)); // Check-in at 10:00
    }

    @Test
    public void calculateValue_ForMotorcycle_ReturnsExpectedValue() {
        when(ticket.getDateTimeExit()).thenReturn(LocalDateTime.of(2024, 10, 1, 12, 0)); // Check-out at 12:00

        Vehicle vehicle = new Vehicle();
        vehicle.setTypeVehicle(TypeVehicle.MOTORCYCLE);
        when(ticket.getVehicle()).thenReturn(vehicle);

        payment = new Payment(ticket);

        double expectedValue = 17.0; // (120 * 0.10) + 5
        double calculatedValue = payment.calculateValue();

        assertThat(calculatedValue).isEqualTo(expectedValue);
    }

    @Test
    public void calculateValue_ForPassengerCar_ReturnsExpectedValue() {
        when(ticket.getDateTimeExit()).thenReturn(LocalDateTime.of(2024, 10, 1, 12, 0));  // Check-out at 12:00

        Vehicle vehicle = new Vehicle();
        vehicle.setTypeVehicle(TypeVehicle.PASSENGER_CAR);
        when(ticket.getVehicle()).thenReturn(vehicle);

        payment = new Payment(ticket);

        double expectedValue = 34.0; // 17 * 2
        double calculatedValue = payment.calculateValue();

        assertThat(calculatedValue).isEqualTo(expectedValue);
    }

    @Test
    public void calculateValue_ForMotorcycle_ReturnsMinimumChargeApplies() {
        when(ticket.getDateTimeExit()).thenReturn(LocalDateTime.of(2024, 10, 1, 10, 10));  // Check-out at 10:10

        Vehicle vehicle = new Vehicle();
        vehicle.setTypeVehicle(TypeVehicle.MOTORCYCLE);
        when(ticket.getVehicle()).thenReturn(vehicle);

        payment = new Payment(ticket);

        double expectedValue = 6.0; // ( 10 * 0.10) + 5
        double calculatedValue = payment.calculateValue();

        assertThat(calculatedValue).isEqualTo(expectedValue);
    }
}
