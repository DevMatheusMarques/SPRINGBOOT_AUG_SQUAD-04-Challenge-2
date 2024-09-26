package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @GetMapping("/{id}")
    public Map<String, Object> getVacancyById(@PathVariable Long id) {
        Vacancy vacancy = vacancyService.getVacancyById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("avulsas_ocupadas", vacancy.getSeparated_ocuppied());
        response.put("capacidade_avulsas", vacancy.getSeparated_capacity());
        response.put("mensalistas_ocupadas", vacancy.getMonthly_occupied());
        response.put("capacidade_mensalistas", vacancy.getMonthly_capacity());

        return response;
    }
    @PatchMapping("/{id}")
    public Vacancy updateVacancyCapacity(@PathVariable Long id,
                                         @RequestBody Map<String, Double> capacities) {
        Double newSeparatedCapacity = capacities.get("capacidade_avulsas");
        Double newMonthlyCapacity = capacities.get("capacidade_mensalistas");
        return vacancyService.updateVacancyCapacity(id, newSeparatedCapacity, newMonthlyCapacity);
    }
}
