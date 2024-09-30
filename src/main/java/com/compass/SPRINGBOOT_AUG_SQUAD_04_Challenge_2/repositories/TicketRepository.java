package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing Ticket entities.
 * Provides methods for querying and manipulating ticket data in the database.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Finds all tickets associated with a specific vehicle plate.
     *
     * @param plate the plate number of the vehicle
     * @return a list of tickets associated with the given vehicle plate
     */
    List<Ticket> findByVehicle_Plate(String plate);

    /**
     * Finds an active ticket for a vehicle based on its plate number.
     * An active ticket is defined as one that is currently parked and does not have an exit time recorded.
     *
     * @param plate the plate number of the vehicle
     * @return the active ticket for the specified vehicle plate, or null if no active ticket exists
     */
    @Query("SELECT t FROM Ticket t WHERE t.vehicle.plate = :plate AND t.parked = TRUE AND t.dateTimeExit IS NULL")
    Ticket findActiveTicketByVehiclePlate(@Param("plate") String plate);
}
