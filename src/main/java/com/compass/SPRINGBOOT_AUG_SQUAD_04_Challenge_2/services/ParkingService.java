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

/**
 * The ParkingService class manages the parking lot spaces, including the allocation and release of spaces for vehicles.
 * It keeps track of total spaces, occupied spaces for monthly and separated categories, and provides methods
 * to handle vehicle entries and exits.
 */
@RequiredArgsConstructor
@Service
public class ParkingService {

    private Integer totalSpaces; // Total number of parking spaces
    private Integer capacityMonthly; // Capacity for monthly-paying vehicles
    private Integer capacitySeparated; // Capacity for separated vehicle types
    private Integer occupiedMonthly; // Number of currently occupied monthly spaces
    private Integer occupiedSeparated; // Number of currently occupied separated spaces

    private final VacancyService vacancyService; // Service to handle vacancy-related operations

    private final List<Integer> vacancies = new ArrayList<>(); // List representing the occupancy of parking spaces

    /**
     * Initializes the ParkingService with data from the vacancyService.
     * This method populates the occupied and capacity variables and initializes the vacancies list.
     */
    @PostConstruct
    public void init() {
        try {
            List<VacancyResponseDto> vacanciesBd = vacancyService.getAllVacancies();

            this.occupiedMonthly = vacanciesBd.getFirst().getMonthly_occupied();
            this.occupiedSeparated = vacanciesBd.getFirst().getSeparated_occupied();
            this.capacityMonthly = vacanciesBd.getFirst().getMonthly_capacity();
            this.capacitySeparated = vacanciesBd.getFirst().getSeparated_capacity();

            this.totalSpaces = capacityMonthly + capacitySeparated;

            // Initializes the list of spaces, all starting as free (0)
            for (int i = 0; i < this.totalSpaces; i++) {
                this.vacancies.add(0); // Initially, all spaces are free (0)
            }

            for (int i = 0; i < occupiedMonthly; i++) {
                this.vacancies.set(i, 1); // Marks occupied spaces for monthly vehicles
            }
            for (int i = occupiedMonthly; i < occupiedMonthly + occupiedSeparated; i++) {
                this.vacancies.set(i, 1); // Marks occupied spaces for separated vehicles
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error - " + e.getMessage(), e);
        }
    }

    /**
     * Returns the number of available parking spaces.
     *
     * @return int - Number of available spaces.
     */
    public int getAvailableSpaces() {
        int occupiedSpaces = 0; // Counter for occupied spaces
        for (int vancancy : vacancies) {
            if (vancancy == 1) occupiedSpaces++; // Counts how many spaces are occupied
        }
        return totalSpaces - occupiedSpaces; // Returns the number of free spaces
    }

    /**
     * Finds available parking spaces of a specific size based on the vehicle type and category.
     *
     * @param category Category of the vehicle (monthly or separated).
     * @param type Type of the vehicle (motorcycle, passenger car, or delivery truck).
     * @return int - Index of the first available space or -1 if none is available.
     */
    private int findAvailableSpaces(Category category, TypeVehicle type) {
        int size = type.equals(TypeVehicle.MOTORCYCLE) ? 1 :
                type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                        type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        // Defines the range of spaces to consider, depending on whether it's for monthly or temporary
        int start = category.equals(Category.MONTHLY_PAYER) ? 0 : capacityMonthly;
        int end = category.equals(Category.MONTHLY_PAYER) ? capacityMonthly : vacancies.size();

        // Looks for a block of free spaces of the required size
        for (int i = start; i <= end - size; i++) {
            boolean spaceAvailable = true;
            for (int j = 0; j < size; j++) {
                if (vacancies.get(i + j) == 1) { // Checks if the space is occupied
                    spaceAvailable = false;
                    break; // Exits the inner loop if any space in the block is occupied
                }
            }
            if (spaceAvailable) {
                return i; // Returns the index of the first space in the found block
            }
        }
        return -1; // Returns -1 if there is no block of free spaces of the required size
    }

    /**
     * Handles the entry of a vehicle into the parking lot.
     * It checks for available spaces, updates the occupied counts, and marks the spaces as occupied.
     *
     * @param category Category of the vehicle (monthly or separated).
     * @param type Type of the vehicle (motorcycle, passenger car, or delivery truck).
     * @return List<Integer> - List of indices for the occupied spaces.
     * @throws NoVacanciesAvailableException if no available spaces are found.
     */
    public List<Integer> vehicleEntry(Category category, TypeVehicle type) throws NoVacanciesAvailableException {
        int size = type.equals(TypeVehicle.MOTORCYCLE) ? 1 :
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

        if (isMonthly) {
            occupiedMonthly += size;
            if (occupiedMonthly > capacityMonthly) {
                throw new NoVacanciesAvailableException("No available spaces for monthly");
            }
            vacancyService.updateVacancyOccupied(1L, null, occupiedMonthly);
        } else {
            occupiedSeparated += size;
            if (occupiedSeparated > capacitySeparated) {
                throw new NoVacanciesAvailableException("No available spaces for separated");
            }
            vacancyService.updateVacancyOccupied(1L, occupiedSeparated, null);
        }

        return recoverVacancies(initialSpace, type);
    }

    /**
     * Handles the exit of a vehicle from the parking lot.
     * It frees the spaces occupied by the vehicle and updates the occupied counts.
     *
     * @param initialSpace Index of the first space occupied by the vehicle.
     * @param vehicle The vehicle exiting the parking lot.
     */
    public void vehicleExit(int initialSpace, Vehicle vehicle) {
        TypeVehicle type = vehicle.getTypeVehicle();
        Category category = vehicle.getCategory();

        int size = type.equals(TypeVehicle.MOTORCYCLE) ? 1 :
                type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                        type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        boolean isMonthly = category.equals(Category.MONTHLY_PAYER);

        // Frees the spaces that the vehicle occupied
        for (int i = 0; i < size; i++) {
            vacancies.set(initialSpace + i, 0); // Marks the spaces as free (adjust for 1-based index)
        }

        List<VacancyResponseDto> vacanciesBd = vacancyService.getAllVacancies();

        if (isMonthly) {
            occupiedMonthly -= size;
            vacancyService.updateVacancyOccupied(1L, null, occupiedMonthly);
        } else {
            occupiedSeparated -= size;
            vacancyService.updateVacancyOccupied(1L, occupiedSeparated, null);
        }
    }

    /**
     * Recovers and returns the list of occupied spaces for a specific vehicle.
     *
     * @param initialSpace Index of the first space occupied by the vehicle.
     * @param type Type of the vehicle (motorcycle, passenger car, or delivery truck).
     * @return List<Integer> - List of indices for the occupied spaces.
     */
    public List<Integer> recoverVacancies(int initialSpace, TypeVehicle type) {
        int size = type.equals(TypeVehicle.MOTORCYCLE) ? 1 :
                type.equals(TypeVehicle.PASSENGER_CAR) ? 2 :
                        type.equals(TypeVehicle.DELIVERY_TRUCK) ? 4 : 0;

        List<Integer> vacancies = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            vacancies.add(initialSpace + i); // Marks the spaces as occupied
        }

        return vacancies;
    }
}
