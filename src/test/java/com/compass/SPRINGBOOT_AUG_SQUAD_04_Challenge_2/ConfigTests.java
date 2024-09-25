package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.TimeZone;

@SpringBootTest
public class ConfigTests {

    @Test
    public void testTimezone_withInstant_toReturnBrazilianDatas(){
        Assertions.assertEquals("Horário Padrão de Brasília", TimeZone.getDefault().getDisplayName());
        Assertions.assertEquals("America/Sao_Paulo", TimeZone.getDefault().getID());
    }
}
