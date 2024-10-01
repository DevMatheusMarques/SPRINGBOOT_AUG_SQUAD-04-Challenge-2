package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.IllegalUpdateVacancyException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.VacancyMapper;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VacancyRepository;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @PostConstruct
    public void init() {
        if (vacancyRepository.count() == 0) {
            Vacancy vacancy = new Vacancy();
            vacancy.setSeparated_capacity(300);
            vacancy.setMonthly_capacity(200);
            vacancy.setMonthly_occupied(0);
            vacancy.setSeparated_ocuppied(0);

            vacancyRepository.save(vacancy);
        }
    }

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
                if(newSeparatedCapacity < vacancy.getSeparated_ocuppied()){
                    throw new IllegalUpdateVacancyException("The requested capacity is less than the current number of parked vehicles");
                }
                vacancy.setSeparated_capacity(newSeparatedCapacity);
            }
            if (newMonthlyCapacity != null) {

                if(newMonthlyCapacity < vacancy.getMonthly_occupied()){
                    throw new IllegalUpdateVacancyException("The requested capacity is less than the current number of parked vehicles");
                }
                vacancy.setMonthly_capacity(newMonthlyCapacity);
            }
            return vacancyRepository.save(vacancy);
        } else {
            throw new NoResultsFoundException("Vacancy not found with id: " + id);
        }
    }

    public void updateVacancyOccupied(Long id, Integer newSeparatedOccupied, Integer newMonthlyOccupied) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);
        if (vacancyOptional.isPresent()) {
            Vacancy vacancy = vacancyOptional.get();

            boolean updated = false;

            if (newSeparatedOccupied != null && !newSeparatedOccupied.equals(vacancy.getSeparated_ocuppied())){
                vacancy.setSeparated_ocuppied(newSeparatedOccupied);
                updated = true;
            }
            if (newMonthlyOccupied != null && !newMonthlyOccupied.equals(vacancy.getMonthly_occupied())) {
                vacancy.setMonthly_occupied(newMonthlyOccupied);
                updated = true;
            }

            if (updated) {
                vacancyRepository.save(vacancy);
            }
        } else {
            throw new NoResultsFoundException("Vacancy not found with id: " + id);
        }
    }
}
