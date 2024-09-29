package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketCreateDto {
    private Vehicle vehicle;
    private Integer entryGate;
}
