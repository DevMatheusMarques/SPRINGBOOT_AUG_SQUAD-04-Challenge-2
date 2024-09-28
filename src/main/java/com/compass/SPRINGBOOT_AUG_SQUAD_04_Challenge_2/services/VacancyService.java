package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
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
            if (newMonthlyOccupied != null && !newMonthlyOccupied.equals(vacancy.getMonthly_capacity())) {
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
