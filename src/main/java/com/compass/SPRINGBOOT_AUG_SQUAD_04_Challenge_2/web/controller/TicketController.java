package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Payment;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.TicketService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        String plate = ticket.getVehicle().getPlate();
        Vehicle vehicle = vehicleService.findVehicleByPlate(plate);
        ticket.setVehicle(vehicle);
        ticket.setDateTimeEntry(LocalDateTime.now());
        Ticket newTicket = ticketService.saveTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        List<Ticket> ticket = ticketService.findAllTickets();
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping(value = "plate/{plate}")
    public ResponseEntity<Ticket> getByPlate(@PathVariable String plate) {
        Ticket ticket = ticketService.findTicketByPlate(plate);
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        Ticket ticket = ticketService.findTicketById(id);
        return ResponseEntity.ok().body(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketUpdate = ticketService.findTicketById(id);
        ticketUpdate.setDateTimeExit(ticket.getDateTimeExit());
        ticketUpdate.setExitGate(ticket.getExitGate());

        Payment payment = new Payment(ticketUpdate);

        Double finalPrice = payment.calculateValue();

        ticketUpdate.setFinalPrice(finalPrice);
        ticketService.updateTicket(ticketUpdate);

        return ResponseEntity.ok().body(ticketUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
