package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/tickets/insert-vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/tickets/delete-vehicle.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VehicleTests {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createVehicle_ReturnStatus201(){
        VehicleResponseDto responseBody = testClient.post().uri("/api/v1/vehicles").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new VehicleCreateDto("ABC-1101", "PASSENGER_CAR", "MONTHLY_PAYER"))
                .exchange().expectStatus().isCreated().expectBody(VehicleResponseDto.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getPlate()).isEqualTo("ABC-1101");
        Assertions.assertThat(responseBody.getTypeVehicle()).isEqualTo("PASSENGER_CAR");
        Assertions.assertThat(responseBody.getCategory()).isEqualTo("MONTHLY_PAYER");
    }

    @Test
    public void createVehicle_ReturnStatus400(){
        ErrorMessage responseBody = testClient.post().uri("/api/v1/vehicles").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new VehicleCreateDto("ABC-1101", "DELIVERY_TRUCK", "MONTHLY_PAYER"))
                .exchange().expectStatus().isEqualTo(400).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void getByPlate_ReturnStatus200(){
        VehicleResponseDto responseBody = testClient.get().uri("/api/v1/vehicles/ABC-1111")
                .exchange().expectStatus().isEqualTo(200).expectBody(VehicleResponseDto.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getPlate()).isEqualTo("ABC-1111");
        Assertions.assertThat(responseBody.getTypeVehicle()).isEqualTo("PASSENGER_CAR");
        Assertions.assertThat(responseBody.getCategory()).isEqualTo("MONTHLY_PAYER");
    }

    @Test
    public void getByPlate_ReturnStatus404(){
        ErrorMessage responseBody = testClient.get().uri("/api/v1/vehicles/ABC-1110").exchange().expectStatus().
                isEqualTo(404).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }
    @Test
    public void getAll_ReturnStatus200(){
        List<VehicleResponseDto> responseBody = testClient.get().uri("/api/v1/vehicles")
                .exchange().expectStatus().isEqualTo(200).expectBodyList(VehicleResponseDto.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();

        for (VehicleResponseDto response : responseBody){
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getPlate()).isNotNull();
        Assertions.assertThat(response.getTypeVehicle()).isNotNull();
        Assertions.assertThat(response.getCategory()).isNotNull();
        }
    }

    @Test
    @Sql(scripts = "/sql/tickets/delete-vehicle.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAll_ReturnStatus404(){
        ErrorMessage responseBody = testClient.get().uri("/api/v1/vehicles").exchange().expectStatus().
                isEqualTo(404).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void update_ReturnStatus204(){
        testClient.patch().uri("/api/v1/vehicles/2").contentType(MediaType.APPLICATION_JSON).bodyValue(new VehicleCreateDto("ABC-1101", "PASSENGER_CAR", "MONTHLY_PAYER"))
                .exchange().expectStatus().isNoContent().expectBody().isEmpty();

    }

    @Test
    public void update_ReturnStatus400(){
        ErrorMessage responseBody = testClient.patch().uri("/api/v1/vehicles/6").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new VehicleCreateDto("ABC-1101", "PUBLIC_SERVICE", "MONTHLY_PAYER"))
                .exchange().expectStatus().isEqualTo(400).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void update_ReturnStatus404(){
        ErrorMessage responseBody = testClient.patch().uri("/api/v1/vehicles/60").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new VehicleCreateDto("ABC-1101", "PASSENGER_CAR", "MONTHLY_PAYER"))
                .exchange().expectStatus().isEqualTo(404).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void delete_ReturnStatus204(){
        testClient.delete().uri("/api/v1/vehicles/100").exchange().expectStatus().isNoContent().expectBody().isEmpty();

    }

    @Test
    public void delete_ReturnStatus404(){
        ErrorMessage responseBody = testClient.delete().uri("/api/v1/vehicles/60").exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }
}
