package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoVacanciesAvailableException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private Integer totalSpaces;
    private Integer capacityMonthly ;
    private Integer capacitySeparated;
    private Integer occupiedMonthly ;
    private Integer occupiedSeparated;

    private final VacancyService vacancyService;

    private final List<Integer> vacancies = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            List<VacancyResponseDto> vacanciesBd = vacancyService.getAllVacancies();

            this.occupiedMonthly  = vacanciesBd.getFirst().getMonthly_occupied();
            this.occupiedSeparated = vacanciesBd.getFirst().getSeparated_occupied();
            this.capacityMonthly  = vacanciesBd.getFirst().getMonthly_capacity();
            this.capacitySeparated = vacanciesBd.getFirst().getSeparated_capacity();

            this.totalSpaces = capacityMonthly + capacitySeparated;

            // Initializes the list of spaces, all starting as free (0)
            for (int i = 0; i < this.totalSpaces; i++) {
                this.vacancies.add(0); // Initially, all spaces are free (0
            }

            for (int i = 0; i < occupiedMonthly; i++) {
                this.vacancies.set(i, 1);
            }
            for (int i = occupiedMonthly; i < occupiedMonthly + occupiedSeparated; i++) {
                this.vacancies.set(i, 1);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error - " + e.getMessage(), e);
        }
    }

    public int getAvailableSpaces() {
        int occupiedSpaces  = 0; // Counter for occupied spaces
        for (int vancancy : vacancies) {
            if (vancancy == 1) occupiedSpaces ++; // Counts how many spaces are occupied
        }
        return totalSpaces - occupiedSpaces ; // Returns the number of free spaces
    }

    private int findAvailableSpaces(Category category, TypeVehicle type) {
        int size =  type.equals(TypeVehicle.MOTOCYCLE) ? 1 :
                    type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                    type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        // Defines the range of spaces to consider, depending on whether it's for monthly or temporary
        int start = category.equals(Category.MONTHLY_PAYER) ? 0 : capacityMonthly;
        int end = category.equals(Category.MONTHLY_PAYER) ? capacityMonthly : capacitySeparated;

        // Looks for a block of free spaces of the required size
        for (int i = start; i <= end - size; i++) {
            boolean spaceAvailable  = true;
            for (int j = 0; j < size; j++) {
                if (vacancies.get(i + j) == 1) { // Checks if the space is occupied
                    spaceAvailable  = false;
                    break; // Exits the inner loop if any space in the block is occupied
                }
            }
            if (spaceAvailable ) {
                return i; // Returns the index of the first space in the found block
            }
        }
        return -1; // Returns -1 if there is no block of free spaces of the required size
    }

    public List<Integer> vehicleEntry(Category category, TypeVehicle type) throws NoVacanciesAvailableException {
        int size =  type.equals(TypeVehicle.MOTOCYCLE) ? 1 :
                    type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                    type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        boolean isMonthly = category.equals(Category.MONTHLY_PAYER);

        int initialSpace = findAvailableSpaces(category, type);

        if (initialSpace == -1) {
            throw new NoVacanciesAvailableException(String.format("No available spaces for " + type + " " + (isMonthly ? "monthly" : "temporary")));
        }

        // Occupies the spaces for the vehicle
        for (int i = 0; i < size; i++) {
            vacancies.set(initialSpace + i, 1); // Marks the spaces as occupied
        }

        List<VacancyResponseDto> vacanciesBd = vacancyService.getAllVacancies();

        if (isMonthly) { //
            occupiedMonthly += size;
            vacancyService.updateVacancyOccupied(1L, null, occupiedMonthly);
        } else {
            occupiedSeparated += size;
            vacancyService.updateVacancyOccupied(1L, occupiedSeparated, null );
        }

        return recoverVacancies(initialSpace, type);
    }

    public void vehicleExit(int initialSpace, Vehicle vehicle) {
        TypeVehicle type = vehicle.getTypeVehicle();
        Category category = vehicle.getCategory();

        int size =  type.equals(TypeVehicle.MOTOCYCLE) ? 1 :
                    type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                    type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        boolean isMonthly = category.equals(Category.MONTHLY_PAYER);

        // Frees the spaces that the vehicle occupied
        for (int i = 0; i < size; i++) {
                vacancies.set(initialSpace + i, 0); // Marks the spaces as free (adjust for 1-based index)
        }

        List<VacancyResponseDto> vacanciesBd = vacancyService.getAllVacancies();

        if (isMonthly) { //
            occupiedMonthly -= size;
            vacancyService.updateVacancyOccupied(1L, null, occupiedMonthly);
        } else {
            occupiedSeparated -= size;
            vacancyService.updateVacancyOccupied(1L, occupiedSeparated, null );
        }
    }

    public List<Integer> recoverVacancies(int initialSpace, TypeVehicle type) {
        int size =  type.equals(TypeVehicle.MOTOCYCLE) ? 1 :
                type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                        type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        List<Integer> vagas = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            vagas.add(initialSpace + i); // Marks the spaces as occupied
        }

        return vagas;
    }

}
