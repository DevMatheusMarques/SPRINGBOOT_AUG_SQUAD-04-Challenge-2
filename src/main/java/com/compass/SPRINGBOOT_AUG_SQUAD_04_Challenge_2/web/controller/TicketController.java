package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        Ticket newTicket = ticketService.saveTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(@RequestParam(value = "type_filter", required = false) String typeFilter,
                                         @RequestParam(value = "value_filter", required = false) String valueFilter) {
        List<Ticket> tickets = ticketService.getTickets(typeFilter, valueFilter);
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/{id}/exit")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok().body(updatedTicket);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
