package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.VacancyMapper;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @GetMapping
    public List<VacancyResponseDto> getAllVacancies() {
        List<VacancyResponseDto> vacancies = vacancyService.getAllVacancies();
        return vacancyService.getAllVacancies();
    }

    @PatchMapping("/{id}")
    public VacancyResponseDto updateVacancyCapacity(@PathVariable Long id,
                                                    @RequestBody VacancyCreateDto createDto) {
        Vacancy updatedVacancy   = vacancyService.updateVacancyCapacity(id, createDto.getSeparated_capacity(), createDto.getMonthly_capacity());
        return VacancyMapper.toResponseDto(updatedVacancy);
    }
}
