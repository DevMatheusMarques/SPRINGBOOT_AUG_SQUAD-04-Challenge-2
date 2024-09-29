package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoVacanciesAvailableException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.TicketService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketPostResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.TicketCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.TicketMapper;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "Contains all operations related to resources for registering, editing and reading a user.")
public class TicketController {

    private final TicketService ticketService;

    @Operation(summary = "Issue a new ticket", description = "Resource to issue a new ticket.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ticket issued successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketPostResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "It was not possible to issue the ticket due to lack of necessary data.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "There are no spaces available for the specified vehicle.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<TicketPostResponseDto> create(@RequestBody TicketCreateDto dto) throws NoVacanciesAvailableException {
        Ticket newTicket = ticketService.saveTicket(TicketMapper.toTicket(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(TicketMapper.toPostDto(newTicket));
    }

    @Operation(summary = "Retrieve Tickets", description = "Returns all tickets or part of them based on some passed parameter.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket(s) successfully recovered.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TicketResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "Ticket(s) not found based on received parameters.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(@RequestParam(value = "type_filter", required = false) String typeFilter,
                                         @RequestParam(value = "value_filter", required = false) String valueFilter) {
        List<Ticket> tickets = ticketService.getTickets(typeFilter, valueFilter);
        return ResponseEntity.ok(TicketMapper.toListDto(tickets));
    }

    @Operation(summary = "Update ticket", description = "Update ticket with more informations, like final price and time exit.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket updated suscessfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Ticket not found to update.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PutMapping("/{id}/exit")
    public ResponseEntity<TicketResponseDto> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok().body(TicketMapper.toDto(updatedTicket));
    }

    @Operation(summary = "Delete ticket", description = "Delete the ticket with the id received.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket deleted suscessfully.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Ticket not found to delete.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
