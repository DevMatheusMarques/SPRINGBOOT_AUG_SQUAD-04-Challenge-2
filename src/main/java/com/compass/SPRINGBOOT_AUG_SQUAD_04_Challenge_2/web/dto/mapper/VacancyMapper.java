package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;

/**
 * The VacancyMapper class provides utility methods to convert between
 * Vacancy entities and their corresponding Data Transfer Objects (DTOs).
 */
public class VacancyMapper {

    /**
     * Converts a Vacancy entity to a VacancyResponseDto.
     *
     * @param vacancy The Vacancy entity to be converted.
     * @return A VacancyResponseDto containing the data from the Vacancy entity,
     *         or null if the input is null.
     */
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

    /**
     * Converts a VacancyCreateDto to a Vacancy entity.
     *
     * @param createDto The VacancyCreateDto to be converted.
     * @return A Vacancy entity populated with data from the VacancyCreateDto,
     *         or null if the input is null.
     */
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

    /**
     * Updates an existing Vacancy entity with data from a VacancyCreateDto.
     *
     * @param vacancy   The Vacancy entity to be updated.
     * @param createDto The VacancyCreateDto containing the new data.
     *                   If either parameter is null, the method does nothing.
     */
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
