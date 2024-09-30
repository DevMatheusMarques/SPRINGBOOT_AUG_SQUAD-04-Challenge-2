package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TicketCreateDto is a Data Transfer Object (DTO) used for creating a new ticket.
 * It contains information about the vehicle and the entry cancel number.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketCreateDto {
    // The vehicle associated with the ticket
    private Vehicle vehicle;

    // The entry cancel number associated with the ticket
    private Integer entryCancel;
}
