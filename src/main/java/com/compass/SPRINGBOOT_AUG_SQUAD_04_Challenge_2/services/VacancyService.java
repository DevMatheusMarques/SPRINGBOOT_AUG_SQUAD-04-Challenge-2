package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    public Map<String, Double> getVacancyStatusByType(TypeVehicle type) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findByType(type);
        if (vacancyOptional.isPresent()) {
            Vacancy vacancy = vacancyOptional.get();
            Map<String, Double> response = new HashMap<>();
            response.put("avulsas_ocupadas", vacancy.getSeparated_ocuppied());
            response.put("capacidade_avulsas", vacancy.getSeparated_capacity());
            response.put("mensalistas_ocupadas", vacancy.getMonthly_occupied());
            response.put("capacidade_mensalistas", vacancy.getMonthly_capacity());
            return response;
        } else {
            throw new RuntimeException("Vacancy not found for type: " + type);
        }
    }

    public Vacancy updateVacancyCapacity(Long id, Double newSeparatedCapacity, Double newMonthlyCapacity) {
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
