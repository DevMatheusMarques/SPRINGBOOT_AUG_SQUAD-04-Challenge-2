package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketPostResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(scripts = {"src/test/java/com/compass/SPRINGBOOT_AUG_SQUAD_04_Challenge_2/sql/ticket/insert-vehicle.sql", "src/test/java/com/compass/SPRINGBOOT_AUG_SQUAD_04_Challenge_2/sql/ticket/insert-ticket.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = {"src/test/java/com/compass/SPRINGBOOT_AUG_SQUAD_04_Challenge_2/sql/ticket/delete-ticket.sql", "sql/ticket/delete-vehicle.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

@Sql(scripts = {"/sql/tickets/insert-vehicle.sql", "/sql/tickets/insert-ticket.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/tickets/delete-ticket.sql", "/sql/tickets/delete-vehicle.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TicketsTestsIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createTicket_withValidDatas_returnStatus201(){
        TicketPostResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TicketCreateDto(
                        new Vehicle("ABC-1122", TypeVehicle.PASSENGER_CAR, Category.SINGLE),
                        1
                ))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TicketPostResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getEntryCancel()).isEqualTo(1);
        Assertions.assertThat(responseBody.getVehicle().getPlate()).isEqualTo("ABC-1122");
        Assertions.assertThat(responseBody.getVehicle().getCategory()).isEqualTo(Category.SINGLE);
        Assertions.assertThat(responseBody.getVehicle().getTypeVehicle()).isEqualTo(TypeVehicle.PASSENGER_CAR);


    }
}