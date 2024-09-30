package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * TicketResponseDto is a Data Transfer Object (DTO) used to represent the response
 * of a ticket. It contains details about the vehicle, parked status, formatted
 * entry and exit times, entry and exit cancel numbers, final price, and
 * the list of occupied vacancies.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
    // The vehicle associated with the ticket
    private Vehicle vehicle;

    // The status indicating whether the vehicle is currently parked
    private Boolean parked;

    // The formatted date and time of entry
    private String dateTimeEntryFormatted;

    // The formatted date and time of exit
    private String dateTimeExitFormatted;

    // The entry cancel number associated with the ticket
    private Integer entryCancel;

    // The exit cancel number associated with the ticket
    private Integer exitCancel;

    // The final price calculated for the parking
    private Double finalPrice;

    // A list of integers representing the occupied vacancies
    private List<Integer> vacanciesOccupied;
}
