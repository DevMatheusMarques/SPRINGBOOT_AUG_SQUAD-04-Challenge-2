package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TicketPostResponseDto is a Data Transfer Object (DTO) used for the response
 * when a ticket is created or posted. It contains details about the vehicle,
 * entry cancel number, formatted entry date and time, parked status, and
 * occupied vacancies.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPostResponseDto {
    // The vehicle associated with the ticket
    private Vehicle vehicle;

    // The entry cancel number associated with the ticket
    private Integer entryCancel;

    // The formatted date and time of entry
    private String dateTimeEntryFormatted;

    // The status indicating whether the vehicle is parked
    private Boolean parked;

    // A list of integers representing the occupied vacancies
    private List<Integer> vacanciesOccupied;
}
