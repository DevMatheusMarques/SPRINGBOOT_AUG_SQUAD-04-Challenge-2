package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPostResponseDto {
    private LocalDateTime dateTimeEntry;
    private Integer entryGate;
    private String plate;
}
