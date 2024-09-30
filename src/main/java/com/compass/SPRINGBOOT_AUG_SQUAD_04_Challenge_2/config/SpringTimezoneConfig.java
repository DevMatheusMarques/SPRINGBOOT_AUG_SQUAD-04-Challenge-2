package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.config;

// Import necessary classes and packages
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * The SpringTimezoneConfig class is responsible for configuring the default time zone
 * for the Spring application. It sets the application's time zone to "America/Sao_Paulo".
 */
@Configuration
public class SpringTimezoneConfig {

    /**
     * This method is executed after the Spring bean has been initialized.
     * It sets the default time zone for the application to "America/Sao_Paulo".
     */
    @PostConstruct
    public void timezoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
