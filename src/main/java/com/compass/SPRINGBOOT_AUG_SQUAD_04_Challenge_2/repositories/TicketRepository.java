package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByVehicle_Plate(String plate);

    @Query("SELECT t FROM Ticket t WHERE t.vehicle.plate = :plate AND t.parked = TRUE AND t.dateTimeExit IS NULL")
    Ticket findActiveTicketByVehiclePlate(@Param("plate") String plate);
}
