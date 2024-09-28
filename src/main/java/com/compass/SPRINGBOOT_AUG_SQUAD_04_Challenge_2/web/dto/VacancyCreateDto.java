package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyCreateDto {

    private Integer separated_occupied;
    private Integer separated_capacity;
    private Integer monthly_occupied;
    private Integer monthly_capacity;
}