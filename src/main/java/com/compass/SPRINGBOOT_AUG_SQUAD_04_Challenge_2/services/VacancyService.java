package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.VacancyMapper;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VacancyRepository;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    public List<VacancyResponseDto> getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return vacancies.stream()
                .map(VacancyMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public Vacancy updateVacancyCapacity(Long id, Integer newSeparatedCapacity, Integer newMonthlyCapacity) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);
        if (vacancyOptional.isPresent()) {
            Vacancy vacancy = vacancyOptional.get();
            if (newSeparatedCapacity != null) {
                vacancy.setSeparated_capacity(newSeparatedCapacity);
            }
            if (newMonthlyCapacity != null) {
                vacancy.setMonthly_capacity(newMonthlyCapacity);
            }
            return vacancyRepository.save(vacancy);
        } else {
            throw new RuntimeException("Vacancy not found with id: " + id);
        }
    }

    public Vacancy updateVacancy(Long id, VacancyCreateDto createDto) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);
        if (vacancyOptional.isPresent()) {
            Vacancy vacancy = vacancyOptional.get();

            if (createDto.getSeparated_capacity() != null) {
                vacancy.setSeparated_capacity(createDto.getSeparated_capacity());
            }
            if (createDto.getMonthly_capacity() != null) {
                vacancy.setMonthly_capacity(createDto.getMonthly_capacity());
            }
            if (createDto.getSeparated_occupied() != null) {
                vacancy.setSeparated_ocuppied(createDto.getSeparated_occupied());
            }
            if (createDto.getMonthly_occupied() != null) {
                vacancy.setMonthly_occupied(createDto.getMonthly_occupied());
            }

            return vacancyRepository.save(vacancy);
        } else {
            throw new RuntimeException("Vacancy not found with id: " + id);
        }
    }
}
