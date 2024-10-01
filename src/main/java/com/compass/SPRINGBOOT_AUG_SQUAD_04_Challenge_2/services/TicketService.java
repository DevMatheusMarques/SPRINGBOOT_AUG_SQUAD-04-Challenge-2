package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

//import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.ParkingService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Payment;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoVacanciesAvailableException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.VehicleInParkingException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.VehicleNotRegisteredException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Service class responsible for managing ticket operations within the parking system.
 * It handles creating, retrieving, updating, and deleting tickets, as well as
 * validating vehicle entries and exits.
 */
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;  // Repository for ticket data access
    private final VehicleService vehicleService;      // Service for vehicle operations
//    private final ParkingService parkingService;      // Service for parking operations

    /**
     * Saves a new ticket into the system. If the vehicle is not registered,
     * it registers the vehicle based on its type. It also checks for active
     * tickets to prevent duplicate entries for the same vehicle.
     *
     * @param ticket the ticket to be saved
     * @return the saved Ticket object
     * @throws NoVacanciesAvailableException if there are no available parking spaces
     */
    @Transactional
    public Ticket saveTicket(Ticket ticket) throws NoVacanciesAvailableException {
        String plate = ticket.getVehicle().getPlate();
        TypeVehicle typeVehicle = ticket.getVehicle().getTypeVehicle();
        Vehicle vehicle = vehicleService.findTicketVehicleByPlate(plate);
        Ticket activeTicket = findActiveTicketByVehiclePlate(plate);

        if (vehicle == null) {
            vehicle = new Vehicle();
            if (typeVehicle == TypeVehicle.PUBLIC_SERVICE) {
                vehicle.setCategory(Category.PUBLIC_SERVICE);
            } else if (typeVehicle == TypeVehicle.DELIVERY_TRUCK) {
                throw new VehicleNotRegisteredException("This type of vehicle must be previously registered in the system.");
            } else {
                vehicle.setCategory(Category.SINGLE);
            }
            vehicle.setTypeVehicle(typeVehicle);
            vehicle.setPlate(ticket.getVehicle().getPlate());
            vehicle.setRegistered(Boolean.FALSE);
            vehicle.setDateCreated(LocalDateTime.now());
            vehicleService.saveVehicleTicket(vehicle);
        }
        if (activeTicket != null) {
            throw new VehicleInParkingException("This vehicle is already in the parking lot.");
        }

        boolean allowedEntry = CancelService.allowEntry(vehicle.getTypeVehicle(), ticket.getEntryCancel());

        if (allowedEntry) {
            if(!vehicle.getTypeVehicle().equals(TypeVehicle.PUBLIC_SERVICE)){
                List<Integer> occupiedSpaces = parkingService.vehicleEntry(vehicle.getCategory(), vehicle.getTypeVehicle());
                ticket.setVacanciesOccupied(occupiedSpaces);
                Integer entry = occupiedSpaces.getFirst();
                ticket.setInitialVacancyOccupied(entry);
            }
        }
        ticket.setVehicle(vehicle);
        ticket.setParked(Boolean.TRUE);
        ticket.setDateTimeEntry(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    /**
     * Retrieves all tickets from the system.
     *
     * @return a list of all Ticket objects
     */
    @Transactional(readOnly = true)
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Finds a ticket by its ID.
     *
     * @param id the ID of the ticket to find
     * @return the found Ticket object
     * @throws NoResultsFoundException if no ticket is found with the given ID
     */
    @Transactional(readOnly = true)
    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NoResultsFoundException("Ticket not found"));
    }

    /**
     * Finds tickets associated with a specific vehicle plate.
     *
     * @param plate the plate of the vehicle to search for tickets
     * @return a list of Ticket objects associated with the specified plate
     * @throws NoResultsFoundException if no tickets are found for the given plate
     */
    @Transactional(readOnly = true)
    public List<Ticket> findTicketsByPlate(String plate) {
        List<Ticket> tickets = ticketRepository.findByVehicle_Plate(plate);
        if (tickets.isEmpty()) {
            throw new NoResultsFoundException("No tickets found for vehicle with plate: " + plate);
        }
        return tickets;
    }

    /**
     * Retrieves tickets based on specified filters (either by ID or plate).
     *
     * @param typeFilter the type of filter (either "id" or "plate")
     * @param valueFilter the value to filter by
     * @return a list of matching Ticket objects
     * @throws IllegalArgumentException if the filter type is invalid
     */
    @Transactional(readOnly = true)
    public List<Ticket> getTickets(String typeFilter, String valueFilter) {
        if (typeFilter == null || valueFilter == null) {
            return findAllTickets();
        }
        if (typeFilter.equalsIgnoreCase("id")) {
            Ticket ticket = findTicketById(Long.valueOf(valueFilter));
            return Collections.singletonList(ticket);
        } else if (typeFilter.equalsIgnoreCase("plate")) {
            return findTicketsByPlate(valueFilter);
        } else {
            throw new IllegalArgumentException("Invalid filter type");
        }
    }

    /**
     * Updates an existing ticket. It checks if the ticket is active before updating
     * the exit time and calculates the final price based on the vehicle type.
     *
     * @param id the ID of the ticket to update
     * @param ticket the Ticket object with updated information
     * @return the updated Ticket object
     * @throws IllegalStateException if trying to update an inactive ticket
     */
    @Transactional
    public Ticket updateTicket(Long id, Ticket ticket) {
        Ticket ticketToUpdate = findTicketById(id);
        Ticket activeTicket = findActiveTicketByVehiclePlate(ticketToUpdate.getVehicle().getPlate());
        TypeVehicle typeVehicle = activeTicket.getVehicle().getTypeVehicle();
        Category category = activeTicket.getVehicle().getCategory();

        if (!activeTicket.getId().equals(ticketToUpdate.getId())) {
            throw new IllegalStateException("You cannot update an inactive ticket.");
        }

        ticketToUpdate.setDateTimeExit(LocalDateTime.now());
        ticketToUpdate.setExitCancel(ticket.getExitCancel());

        boolean allowedExit = CancelService.allowExit(ticketToUpdate.getVehicle().getTypeVehicle(), ticketToUpdate.getExitCancel());
        if (allowedExit) {
            if(!ticketToUpdate.getVehicle().getTypeVehicle().equals(TypeVehicle.PUBLIC_SERVICE)) {
                parkingService.vehicleExit(ticketToUpdate.getInitialVacancyOccupied(), ticketToUpdate.getVehicle());
            }
        }

        if (typeVehicle == TypeVehicle.DELIVERY_TRUCK || typeVehicle == TypeVehicle.PUBLIC_SERVICE || category == Category.MONTHLY_PAYER) {
            ticketToUpdate.setFinalPrice(0.0);
        } else {
            Payment payment = new Payment(ticketToUpdate);
            Double finalPrice = payment.calculateValue();
            ticketToUpdate.setFinalPrice(finalPrice);
        }

        ticketToUpdate.setParked(Boolean.FALSE);

        return ticketRepository.save(ticketToUpdate);
    }

    /**
     * Finds an active ticket based on the vehicle's plate.
     *
     * @param plate the plate of the vehicle
     * @return the active Ticket object if found
     */
    public Ticket findActiveTicketByVehiclePlate(String plate) {
        return ticketRepository.findActiveTicketByVehiclePlate(plate);
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param id the ID of the ticket to delete
     */
    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = findTicketById(id);
        ticketRepository.deleteById(ticket.getId());
    }
}
