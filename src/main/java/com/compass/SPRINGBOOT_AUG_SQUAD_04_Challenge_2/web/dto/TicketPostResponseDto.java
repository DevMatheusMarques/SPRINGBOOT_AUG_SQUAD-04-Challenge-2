package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPostResponseDto {
    private Long id;
    private Vehicle vehicle;
    private String plate;
    private Boolean parked;
    private LocalDateTime dateTimeEntry;
    private Integer entryCancel;
    private List<Integer> vacanciesOccupied;
}
