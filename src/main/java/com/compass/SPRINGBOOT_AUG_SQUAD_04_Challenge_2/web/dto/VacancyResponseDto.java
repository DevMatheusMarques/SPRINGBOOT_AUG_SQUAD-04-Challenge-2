package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyResponseDto {

    private Integer separated_occupied;
    private Integer separated_capacity;
    private Integer monthly_occupied;
    private Integer monthly_capacity;
}