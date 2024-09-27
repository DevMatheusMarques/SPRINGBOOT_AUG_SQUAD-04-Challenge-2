package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
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
}
