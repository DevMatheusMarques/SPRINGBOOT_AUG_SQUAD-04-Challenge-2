package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Endpoints Documentation (Parking API)")
                        .version("1.0.0")
                        .description("This API allows you to manage a parking lot, containing options to manage spaces, vehicles and tickets."));
    }
}
