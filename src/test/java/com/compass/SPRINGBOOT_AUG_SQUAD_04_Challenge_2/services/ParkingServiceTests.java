package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoVacanciesAvailableException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTests {

    @InjectMocks
    private ParkingService parkingService;

    @Mock
    private VacancyService vacancyService;

    @BeforeEach
    public void init() { // vacancy service
        VacancyResponseDto vacancyResponseDto = new VacancyResponseDto();
        vacancyResponseDto.setMonthly_capacity(10);
        vacancyResponseDto.setSeparated_capacity(5);
        vacancyResponseDto.setMonthly_occupied(0);
        vacancyResponseDto.setSeparated_occupied(0);

        when(vacancyService.getAllVacancies()).thenReturn(Collections.singletonList(vacancyResponseDto));
    }

    @Test
    public void getAvailableSpaces_WithAvailableSpacesMethod_ReturnsAllVacancies() {
        parkingService.init();

        int availableSpaces = parkingService.getAvailableSpaces();
        assertThat(availableSpaces).isEqualTo(15); // total capacity = 10 + 5
    }

    @Test
    public void vehicleEntry_WithAvailableSpacesMethod_ReturnFirstVehicleVacancyAndAvailableSpace() throws NoVacanciesAvailableException {
        parkingService.init();

        Vehicle vehicle = new Vehicle(); // simulating vehicle
        vehicle.setTypeVehicle(TypeVehicle.MOTORCYCLE);
        vehicle.setCategory(Category.MONTHLY_PAYER);

        List<Integer> occupiedSpaces = parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.MOTORCYCLE);

        assertThat(occupiedSpaces).containsExactly(0); // first vacancy
        assertThat(parkingService.getAvailableSpaces()).isEqualTo(14); // expected amount of free vacancies
    }

    @Test
    public void vehicleEntry_NoVacanciesAvailable_ThrowsNoVacanciesAvailableException() {
        parkingService.init();

        Vehicle vehicle = new Vehicle(); // simulating vehicle
        vehicle.setTypeVehicle(TypeVehicle.PASSENGER_CAR);
        vehicle.setCategory(Category.MONTHLY_PAYER);

        for (int i = 0; i < 10; i++) { // occupying all available vacancies
            try {
                parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.PASSENGER_CAR);
            } catch (NoVacanciesAvailableException ex) {
            }
        }

        NoVacanciesAvailableException exception = assertThrows(NoVacanciesAvailableException.class, () -> { // launching the exception
            parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.PASSENGER_CAR);
        });

        assertThat(exception.getMessage()).contains("No available spaces for PASSENGER_CAR monthly"); // expected message
    }

    @Test
    public void vehicleEntry_WithExceedingCapacity_ThrowsNoVacanciesAvailableException() {
        parkingService.init();

        Vehicle vehicle = new Vehicle();
        vehicle.setTypeVehicle(TypeVehicle.PASSENGER_CAR);
        vehicle.setCategory(Category.MONTHLY_PAYER);

        for (int i = 0; i < 9; i++) { // occupying all available vacancies
            try {
                parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.PASSENGER_CAR);
            } catch (NoVacanciesAvailableException ex) {
            }
        }

        NoVacanciesAvailableException exception = assertThrows(NoVacanciesAvailableException.class, () -> {
            parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.PASSENGER_CAR);
        });

        assertEquals("No available spaces for PASSENGER_CAR monthly", exception.getMessage());
    }
    

    @Test
    public void vehicleExit_ReturnFreesTheVacancies() {
        parkingService.init();

        Vehicle vehicle = new Vehicle(); // simulating vehicle
        vehicle.setTypeVehicle(TypeVehicle.MOTORCYCLE);
        vehicle.setCategory(Category.MONTHLY_PAYER);

        List<Integer> occupiedSpaces = parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.MOTORCYCLE);
        parkingService.vehicleExit(occupiedSpaces.getFirst(), vehicle); // simulating exit of the vehicle

        assertThat(parkingService.getAvailableSpaces()).isEqualTo(15); // expected amount of free vacancies
    }
}
