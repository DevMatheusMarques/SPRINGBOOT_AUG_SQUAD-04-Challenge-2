package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;

public class VacancyMapper {

    public static VacancyResponseDto toResponseDto(Vacancy vacancy) {
        if (vacancy == null) {
            return null;
        }

        VacancyResponseDto vacancyResponseDto = new VacancyResponseDto();
        vacancyResponseDto.setSeparated_occupied(vacancy.getSeparated_ocuppied());
        vacancyResponseDto.setSeparated_capacity(vacancy.getSeparated_capacity());
        vacancyResponseDto.setMonthly_occupied(vacancy.getMonthly_occupied());
        vacancyResponseDto.setMonthly_capacity(vacancy.getMonthly_capacity());

        return vacancyResponseDto;
    }

    public static Vacancy toEntityFromCreateDto(VacancyCreateDto createDto) {
        if (createDto == null) {
            return null;
        }

        Vacancy vacancy = new Vacancy();
        vacancy.setSeparated_ocuppied(createDto.getSeparated_occupied());
        vacancy.setSeparated_capacity(createDto.getSeparated_capacity());
        vacancy.setMonthly_occupied(createDto.getMonthly_occupied());
        vacancy.setMonthly_capacity(createDto.getMonthly_capacity());

        return vacancy;
    }

    public static void updateEntityFromDto(Vacancy vacancy, VacancyCreateDto createDto) {
        if (vacancy == null || createDto == null) {
            return;
        }

        vacancy.setSeparated_ocuppied(createDto.getSeparated_occupied());
        vacancy.setSeparated_capacity(createDto.getSeparated_capacity());
        vacancy.setMonthly_occupied(createDto.getMonthly_occupied());
        vacancy.setMonthly_capacity(createDto.getMonthly_capacity());
    }
}
