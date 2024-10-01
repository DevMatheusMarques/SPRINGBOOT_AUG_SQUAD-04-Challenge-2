package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.TicketRepository;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VacancyResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private ParkingService parkingService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private VacancyService vacancyService;

    Vehicle vehicle = new Vehicle(1L, Boolean.TRUE, "XYZ-1234", Category.MONTHLY_PAYER, TypeVehicle.PASSENGER_CAR, null, null, null);
    Ticket ticket = new Ticket(1L, Boolean.TRUE, LocalDateTime.now(), null, 1, null, null, 0, List.of(0, 1), vehicle);

    @Test
    public void saveTicket_WithValidData_ReturnTicket() {
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket sut = ticketService.saveTicket(ticket);

        assertThat(sut).isEqualTo(ticket);
    }

    @Test
    public void deleteTicket_WithExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> ticketService.deleteTicket(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteTicket_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(ticketRepository).deleteById(99L);

        assertThatThrownBy(() -> ticketService.deleteTicket(99L)).isInstanceOf(RuntimeException.class);
    }

}
