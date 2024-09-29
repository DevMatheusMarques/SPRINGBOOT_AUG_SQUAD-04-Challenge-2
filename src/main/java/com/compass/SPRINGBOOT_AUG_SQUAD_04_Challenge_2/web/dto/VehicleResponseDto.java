package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VehicleResponseDto {
    private long id;
    private String plate;
    private String type;
    private String category;
    private boolean registered;
}
