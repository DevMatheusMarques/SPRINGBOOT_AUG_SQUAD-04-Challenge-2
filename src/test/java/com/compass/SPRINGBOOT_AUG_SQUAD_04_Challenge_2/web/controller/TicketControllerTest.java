package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.TicketService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketPostResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.TicketMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @Test
    void create() {
        TicketCreateDto ticketCreateDto = new TicketCreateDto();

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC-1234");
        savedTicket.setVehicle(vehicle);
        savedTicket.setDateTimeEntry(LocalDateTime.now());

        when(ticketService.saveTicket(any(Ticket.class))).thenReturn(savedTicket);

        TicketPostResponseDto responseDto = new TicketPostResponseDto();
        responseDto.setVehicle(savedTicket.getVehicle());
        responseDto.setEntryCancel(savedTicket.getEntryCancel());
        responseDto.setParked(savedTicket.getParked());
        responseDto.setDateTimeEntryFormatted(savedTicket.getDateTimeEntry().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        ResponseEntity<TicketPostResponseDto> result = ticketController.create(ticketCreateDto);

        assertNotNull(result);
        assertEquals(responseDto.getVehicle().getPlate(), result.getBody().getVehicle().getPlate());
        assertEquals(responseDto.getParked(), result.getBody().getParked());
        verify(ticketService, times(1)).saveTicket(any(Ticket.class));
    }

    @Test
    void getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        when(ticketService.getTickets(null, null)).thenReturn(tickets);

        ResponseEntity<List<TicketResponseDto>> responseEntity = ticketController.getAllTickets(null, null);

        List<TicketResponseDto> result = responseEntity.getBody();

        assertNotNull(result);
        assertEquals(tickets, result);
        verify(ticketService, times(1)).getTickets(null, null);
    }

    @Test
    void update() {
        Long ticketId = 1L;

        Ticket updatedTicket = new Ticket();
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC-1234");
        updatedTicket.setVehicle(vehicle);
        updatedTicket.setDateTimeEntry(LocalDateTime.now());

        when(ticketService.updateTicket(ticketId, updatedTicket)).thenReturn(updatedTicket);

        ResponseEntity<TicketResponseDto> result = ticketController.update(ticketId, updatedTicket);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        TicketResponseDto responseDto = TicketMapper.toDto(updatedTicket);
        assertEquals(responseDto.getVehicle().getPlate(), result.getBody().getVehicle().getPlate());
        assertEquals(responseDto.getDateTimeEntryFormatted(), result.getBody().getDateTimeEntryFormatted());

        verify(ticketService, times(1)).updateTicket(ticketId, updatedTicket);
    }


    @Test
    void delete() {
        Long ticketId = 1L;
        doNothing().when(ticketService).deleteTicket(ticketId);

        ticketController.delete(ticketId);

        verify(ticketService, times(1)).deleteTicket(ticketId);
    }
}
