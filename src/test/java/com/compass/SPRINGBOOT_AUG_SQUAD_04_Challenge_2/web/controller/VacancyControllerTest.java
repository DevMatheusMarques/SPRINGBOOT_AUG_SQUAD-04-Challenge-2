package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacancyControllerTest {

    @Mock
    private VacancyService vacancyService;

    @InjectMocks
    private VacancyController vacancyController;

    @Test
    void getAllVacancies() {
        List<VacancyResponseDto> vacanciesResponse = new ArrayList<>();

        when(vacancyService.getAllVacancies()).thenReturn(vacanciesResponse);

        List<VacancyResponseDto> result = vacancyController.getAllVacancies();

        assertNotNull(result);
        assertEquals(vacanciesResponse, result);
    }

    @Test
    void updateVacancyCapacity() {
        VacancyCreateDto dto = new VacancyCreateDto();
        dto.setSeparated_capacity(30);
        dto.setMonthly_capacity(20);

        Vacancy vacancy1 = new Vacancy();
        vacancy1.setSeparated_capacity(35);
        vacancy1.setMonthly_capacity(25);

        when(vacancyService.updateVacancyCapacity(1L, dto.getSeparated_capacity(), dto.getMonthly_capacity()))
                .thenReturn(vacancy1);

        VacancyResponseDto responseDto = vacancyController.updateVacancyCapacity(1L, dto);

        assertNotNull(responseDto);
        assertEquals(vacancy1.getMonthly_capacity(), responseDto.getMonthly_capacity());
        assertEquals(vacancy1.getSeparated_capacity(), responseDto.getSeparated_capacity());
    }
}
