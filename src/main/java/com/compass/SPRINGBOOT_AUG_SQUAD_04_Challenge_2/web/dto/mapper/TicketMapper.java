package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper;

import ch.qos.logback.core.model.Model;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketPostResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketCreateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {
    public static Ticket toTicket(TicketCreateDto dto){
        return new ModelMapper().map(dto, Ticket.class);
    }

    public static TicketResponseDto toDto(Ticket ticket){
        String plate = ticket.getVehicle().getPlate();
        PropertyMap<Ticket, TicketResponseDto> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setPlate(plate);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(ticket, TicketResponseDto.class);
    }

    public static List<TicketResponseDto> toListDto(List<Ticket> listDto){
        return listDto.stream().map(TicketMapper::toDto).toList();
    }

    public static TicketPostResponseDto toPostDto(Ticket ticket){
        String plate = ticket.getVehicle().getPlate();
        PropertyMap<Ticket, TicketPostResponseDto> props = new PropertyMap<Ticket, TicketPostResponseDto>() {
            @Override
            protected void configure() {
                map().setPlate(plate);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(ticket, TicketPostResponseDto.class);
    }
}
