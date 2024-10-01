package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

// Import necessary classes and packages
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

/**
 * The VacancyService class provides methods to manage and manipulate vacancy data
 * within the system. It includes functionalities to retrieve, update, and manage
 * vacancy capacities and occupied spaces.
 */
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

    /**
     * Retrieves a list of all vacancies in the system and converts them into
     * VacancyResponseDto objects.
     *
     * @return List of VacancyResponseDto containing details of each vacancy.
     */
    public List<VacancyResponseDto> getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return vacancies.stream()
                .map(VacancyMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates the capacity of a specific vacancy identified by its ID.
     * If the new capacity is less than the number of occupied spaces,
     * an IllegalUpdateVacancyException is thrown.
     *
     * @param id                  The ID of the vacancy to update.
     * @param newSeparatedCapacity The new separated capacity for the vacancy.
     * @param newMonthlyCapacity   The new monthly capacity for the vacancy.
     * @return The updated Vacancy object.
     * @throws IllegalUpdateVacancyException if the requested capacity is less than the current occupied spaces.
     * @throws NoResultsFoundException        if no vacancy is found with the provided ID.
     */
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

    /**
     * Updates the number of occupied spaces for a specific vacancy identified by its ID.
     *
     * @param id                   The ID of the vacancy to update.
     * @param newSeparatedOccupied  The new number of separated occupied spaces.
     * @param newMonthlyOccupied    The new number of monthly occupied spaces.
     * @throws NoResultsFoundException if no vacancy is found with the provided ID.
     */
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
