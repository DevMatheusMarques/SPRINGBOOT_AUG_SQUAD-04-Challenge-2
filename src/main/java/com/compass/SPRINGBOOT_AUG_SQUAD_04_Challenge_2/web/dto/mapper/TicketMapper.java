package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper;

// Import necessary classes and packages
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketPostResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketCreateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * TicketMapper is a utility class that provides methods to convert between Ticket entities and
 * their corresponding Data Transfer Objects (DTOs).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {
    // DateTimeFormatter to format date and time in a specific pattern
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");

    /**
     * Converts a TicketCreateDto to a Ticket entity.
     *
     * @param dto the TicketCreateDto to convert
     * @return a Ticket entity
     */
    public static Ticket toTicket(TicketCreateDto dto){
        return new ModelMapper().map(dto, Ticket.class);
    }

    /**
     * Converts a Ticket entity to a TicketResponseDto.
     *
     * @param ticket the Ticket entity to convert
     * @return a TicketResponseDto
     */
    public static TicketResponseDto toDto(Ticket ticket){
        String plate = ticket.getVehicle().getPlate();
        LocalDateTime dateTimeEntry = ticket.getDateTimeEntry();
        LocalDateTime dateTimeExit = ticket.getDateTimeExit();

        // Format entry and exit date times
        String dateTimeEntryFormetted = dateTimeEntry.format(fmt);
        String dateTimeExitFormatted;
        if (!(dateTimeExit == null)){
            dateTimeExitFormatted = dateTimeExit.format(fmt);
        } else {
            dateTimeExitFormatted = "";
        }

        // Create a PropertyMap to map specific properties to the DTO
        PropertyMap<Ticket, TicketResponseDto> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setDateTimeEntryFormatted(dateTimeEntryFormetted);
                map().setDateTimeExitFormatted(dateTimeExitFormatted);
            }
        };

        // Create a ModelMapper instance and add the mappings
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        return mapper.map(ticket, TicketResponseDto.class);
    }

    /**
     * Converts a list of Ticket entities to a list of TicketResponseDto.
     *
     * @param listDto the list of Ticket entities to convert
     * @return a list of TicketResponseDto
     */
    public static List<TicketResponseDto> toListDto(List<Ticket> listDto){
        return listDto.stream().map(TicketMapper::toDto).toList();
    }

    /**
     * Converts a Ticket entity to a TicketPostResponseDto.
     *
     * @param ticket the Ticket entity to convert
     * @return a TicketPostResponseDto
     */
    public static TicketPostResponseDto toPostDto(Ticket ticket){
        String plate = ticket.getVehicle().getPlate();
        LocalDateTime dateTimeEntry = ticket.getDateTimeEntry();
        String dateTimeEntryFormetted = dateTimeEntry.format(fmt);

        // Create a PropertyMap to map specific properties to the DTO
        PropertyMap<Ticket, TicketPostResponseDto> props = new PropertyMap<Ticket, TicketPostResponseDto>() {
            @Override
            protected void configure() {
                map().setDateTimeEntryFormatted(dateTimeEntryFormetted);
            }
        };

        // Create a ModelMapper instance and add the mappings
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        return mapper.map(ticket, TicketPostResponseDto.class);
    }
}
