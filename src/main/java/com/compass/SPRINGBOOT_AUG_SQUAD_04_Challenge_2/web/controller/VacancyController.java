package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.VacancyMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Operation(summary = "Check occupied and unoccupied vacancies.", description = "Returns the query of occupied and unoccupied vacancies.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns parking spaces and capacity.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VacancyResponseDto.class)))
            })
    @GetMapping
    public List<VacancyResponseDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @Operation(summary = "Updates the number of vacancies available for monthly and occasional employees.", description = "Updates the number of vacancies available for monthly and occasional employees.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Returns that the update was successful.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VacancyResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Returns that the requested capacity is invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VacancyResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Returns that the requested capacity is less than the current number of parked vehicles.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VacancyResponseDto.class))),
            })
    @PatchMapping("/{id}")
    public VacancyResponseDto updateVacancyCapacity(@PathVariable Long id,
                                                    @RequestBody VacancyCreateDto createDto) {
        Vacancy updatedVacancy   = vacancyService.updateVacancyCapacity(id, createDto.getSeparated_capacity(), createDto.getMonthly_capacity());
        return VacancyMapper.toResponseDto(updatedVacancy);
    }
}
