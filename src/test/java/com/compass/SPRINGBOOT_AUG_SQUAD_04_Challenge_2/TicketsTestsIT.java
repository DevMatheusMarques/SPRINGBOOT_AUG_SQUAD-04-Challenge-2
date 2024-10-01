package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.ParkingService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VacancyService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VehicleService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.*;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/tickets/insert-vacancies.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/tickets/insert-vehicle.sql", "/sql/tickets/insert-ticket.sql", "/sql/tickets/insert-ticket_vacancies.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/tickets/delete-ticket_vacancies.sql", "/sql/tickets/delete-ticket.sql", "/sql/tickets/delete-vehicle.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/sql/tickets/delete-vacancies.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class TicketsTestsIT {

    @Autowired
    WebTestClient testClient;

    @MockBean
    private ParkingService parkingService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VacancyService vacancyService;

    @Test
    public void createTicket_withValidDatas_returnStatus201() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("AAA-1111");
        TicketPostResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TicketCreateDto(vehicle, 3))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TicketPostResponseDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void createTicket_withInvalidDatas_returnErrorMessageStatus400(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TicketCreateDto(
                        new Vehicle("AAA-0101", TypeVehicle.PASSENGER_CAR, Category.SINGLE), 15)) //Invalid cancel
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
        Assertions.assertThat(responseBody.getMethod()).isEqualTo("POST");
    }

    @Test
    public void getAllTickets_withoutParameters_returnStatus200(){
        List<TicketResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/tickets")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TicketResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.size()).isEqualTo(1);
    }

    @Test
    public void updateTicket_withValidIdTicket_returnStatus200(){
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicleService.findVehicleById(1L));
        ticket.setParked(true);
        ticket.setEntryCancel(1);
        ticket.setExitCancel(6);
        ticket.setId(1L);
        ticket.setDateTimeExit(LocalDateTime.now());
        TicketResponseDto responseBody = testClient
                .put()
                .uri("api/v1/tickets/1/exit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ticket)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TicketResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getExitCancel()).isEqualTo(6);
    }

    @Test
    public void updateTicket_withInvalidIdTicket_returnStatus200(){
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicleService.findVehicleById(1L));
        ticket.setParked(true);
        ticket.setEntryCancel(1);
        ticket.setExitCancel(6);
        ticket.setId(80L);
        ticket.setDateTimeExit(LocalDateTime.now());
        ErrorMessage responseBody = testClient
                .put()
                .uri("api/v1/tickets/{ticket_id}/exit", 80)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ticket)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void deleteTicket_withValidIdTicket_returnStatus204(){
        testClient
                .delete()
                .uri("api/v1/tickets/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void deleteTicket_withInvalidIdTicket_returnStatus404(){
        testClient
                .delete()
                .uri("api/v1/tickets/9")
                .exchange()
                .expectStatus().isNotFound();
    }

}