package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
    private String plate;
    private Boolean parked;
    private String dateTimeEntryFormatted;
    private String dateTimeExitFormatted;
    private Integer entryCancel;
    private Integer exitCancel;
    private Double finalPrice;
    private List<Integer> vacanciesOccupied;
}
