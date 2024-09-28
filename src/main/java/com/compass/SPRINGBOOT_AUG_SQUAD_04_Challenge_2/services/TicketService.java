package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Payment;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VehicleService vehicleService;

    @Transactional
    public Ticket saveTicket(Ticket ticket) {
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
            vehicleService.saveVehicleTicket(vehicle);
        }
        if (activeTicket != null) {
            throw new IllegalStateException("This vehicle is already in the parking lot.");
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
        ticketToUpdate.setDateTimeExit(ticket.getDateTimeExit());
        ticketToUpdate.setExitGate(ticket.getExitGate());

        Payment payment = new Payment(ticketToUpdate);
        Double finalPrice = payment.calculateValue();

        ticketToUpdate.setParked(Boolean.FALSE);
        ticketToUpdate.setFinalPrice(finalPrice);

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
