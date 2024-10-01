package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vacancy;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.IllegalUpdateVacancyException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.VacancyRepository;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.ParkingService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class VacancyServiceTest {
    @Autowired
    private VacancyService vacancyService;

    @MockBean
    private VacancyRepository vacancyRepository;

    @MockBean
    private ParkingService parkingService;

    @Test
    public void getAllVacancy_returnAll() {

        Vacancy vacancy1 = new Vacancy(1L, 5, 10, 2, 8);
        when(vacancyRepository.findAll()).thenReturn(Arrays.asList(vacancy1));
        List<VacancyResponseDto> vacancies = vacancyService.getAllVacancies();

        assertThat(vacancies).hasSize(1);
        assertThat(vacancies.get(0).getSeparated_capacity()).isEqualTo(10);

    }

    @Test
    public void updateVacancyCapacity_validId_updateCapacity() {
        Vacancy existingVacancy = new Vacancy(1L, 10, 20, 5, 15);

        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(existingVacancy));
        when(vacancyRepository.save(Mockito.any(Vacancy.class))).thenAnswer(i -> i.getArguments()[0]);

        Vacancy updatedVacancy = vacancyService.updateVacancyCapacity(1L, 25, 18);

        assertThat(updatedVacancy.getSeparated_capacity()).isEqualTo(25);
        assertThat(updatedVacancy.getMonthly_capacity()).isEqualTo(18);
    }

    @Test
    public void updateVacancyCapacity_invalidId_throwsException() {

        Vacancy existingVacancy = new Vacancy(1L, 10, 20, 5, 15);

        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(existingVacancy));

        assertThatThrownBy(() -> vacancyService.updateVacancyCapacity(2L, 15, 18))
                .isInstanceOf(NoResultsFoundException.class)
                .hasMessage("Vacancy not found with id: " + 2L);


    }
}
