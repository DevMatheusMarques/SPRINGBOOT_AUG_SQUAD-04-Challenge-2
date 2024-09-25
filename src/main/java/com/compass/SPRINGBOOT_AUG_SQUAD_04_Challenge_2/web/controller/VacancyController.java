package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @GetMapping("/type/{type}")
    public Map<String, Double> getVacancyStatusByType(@PathVariable TypeVehicle type) {
        return vacancyService.getVacancyStatusByType(type);
    }

    @PatchMapping("/{id}")
    public Vacancy updateVacancyCapacity(@PathVariable Long id,
                                         @RequestBody Map<String, Double> capacities) {
        Double newSeparatedCapacity = capacities.get("capacidade_avulsas");
        Double newMonthlyCapacity = capacities.get("capacidade_mensalistas");
        return vacancyService.updateVacancyCapacity(id, newSeparatedCapacity, newMonthlyCapacity);
    }
}
