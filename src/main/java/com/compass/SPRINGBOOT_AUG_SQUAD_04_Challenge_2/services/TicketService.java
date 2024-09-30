package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Payment;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoVacanciesAvailableException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VehicleService vehicleService;
    private final ParkingService parkingService;

    @Transactional
    public Ticket saveTicket(Ticket ticket) throws NoVacanciesAvailableException {
        String plate = ticket.getVehicle().getPlate();
        TypeVehicle typeVehicle = ticket.getVehicle().getTypeVehicle();
        Vehicle vehicle = vehicleService.findTicketVehicleByPlate(plate);
        Ticket activeTicket = findActiveTicketByVehiclePlate(plate);

        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setCategory(Category.SINGLE);
            vehicle.setTypeVehicle(typeVehicle);
            vehicle.setPlate(ticket.getVehicle().getPlate());
            vehicle.setRegistered(Boolean.FALSE);
            vehicle.setDateCreated(LocalDateTime.now());
            vehicleService.saveVehicleTicket(vehicle);
        }
        if (activeTicket != null) {
            throw new IllegalStateException("This vehicle is already in the parking lot.");
        }

        boolean allowedEntry = CancelService.allowEntry(vehicle.getTypeVehicle(), ticket.getEntryCancel());

        if (allowedEntry) {
            List<Integer> occupiedSpaces = parkingService.vehicleEntry(vehicle.getCategory(), vehicle.getTypeVehicle());
            ticket.setVacanciesOccupied(occupiedSpaces);
            Integer entry = occupiedSpaces.getFirst();
            ticket.setInitialVacancyOccupied(entry);
        }

        ticket.setVehicle(vehicle);
        ticket.setParked(Boolean.TRUE);
        ticket.setDateTimeEntry(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

    @Transactional(readOnly = true)
    public List<Ticket> findTicketsByPlate(String plate) {
        List<Ticket> tickets = ticketRepository.findByVehicle_Plate(plate);
        if (tickets.isEmpty()) {
            throw new EntityNotFoundException("No tickets found for vehicle with plate: " + plate);
        }
        return tickets;
    }

    @Transactional(readOnly = true)
    public List<Ticket> getTickets(String typeFilter, String valueFilter) {
        if (typeFilter == null || valueFilter == null) {
            return findAllTickets();
        }
        if (typeFilter.equalsIgnoreCase("id")) {
            Ticket ticket = findTicketById(Long.valueOf(valueFilter));
            return Collections.singletonList(ticket);
        } else if (typeFilter.equalsIgnoreCase("plate")) {
            return findTicketsByPlate(valueFilter);
        } else {
            throw new IllegalArgumentException("Invalid filter type");
        }
    }

    @Transactional
    public Ticket updateTicket(Long id, Ticket ticket) {
        Ticket ticketToUpdate = findTicketById(id);
        Ticket activeTicket = findActiveTicketByVehiclePlate(ticketToUpdate.getVehicle().getPlate());

        if (activeTicket == null || !activeTicket.getId().equals(ticketToUpdate.getId())) {
            throw new IllegalStateException("You cannot update an inactive ticket.");
        }

        ticketToUpdate.setDateTimeExit(LocalDateTime.now());
        ticketToUpdate.setExitCancel(ticket.getExitCancel());

        boolean allowedExit = CancelService.allowExit(ticketToUpdate.getVehicle().getTypeVehicle(), ticketToUpdate.getExitCancel());

        if (allowedExit) {
            parkingService.vehicleExit(ticketToUpdate.getInitialVacancyOccupied(), ticketToUpdate.getVehicle());
        }

        Payment payment = new Payment(ticketToUpdate);
        Double finalPrice = payment.calculateValue();

        ticketToUpdate.setFinalPrice(finalPrice);

        ticketToUpdate.setParked(Boolean.FALSE);

        return ticketRepository.save(ticketToUpdate);
    }

    public Ticket findActiveTicketByVehiclePlate(String plate) {
        return ticketRepository.findActiveTicketByVehiclePlate(plate);
    }

    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = findTicketById(id);
        ticketRepository.deleteById(ticket.getId());
    }
}
