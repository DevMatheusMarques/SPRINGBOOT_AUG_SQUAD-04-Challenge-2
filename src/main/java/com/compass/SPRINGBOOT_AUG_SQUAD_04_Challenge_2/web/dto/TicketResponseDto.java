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
public class TicketResponseDto {
    private LocalDateTime dateTimeEntry;
    private LocalDateTime dateTimeExit;
    private Integer entryCancel;
    private Integer exitCancel;
    private Double finalPrice;
    private String plate;
}
