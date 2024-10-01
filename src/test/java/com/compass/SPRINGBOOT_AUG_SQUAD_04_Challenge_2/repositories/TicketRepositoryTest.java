package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    private Vehicle vehicle;
    private Ticket activeTicket;
    private Ticket inactiveTicket;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        vehicle = new Vehicle();
        vehicle.setPlate("ABC-1234");
        vehicle.setCategory(Category.MONTHLY_PAYER);
        vehicle.setTypeVehicle(TypeVehicle.PASSENGER_CAR);
        vehicle.setRegistered(true);

        activeTicket = new Ticket();
        activeTicket.setVehicle(vehicle);
        activeTicket.setEntryCancel(1);
        activeTicket.setDateTimeEntry(LocalDateTime.now());
        activeTicket.setParked(true);

        entityManager.persist(vehicle);
        entityManager.persist(activeTicket);

        inactiveTicket = new Ticket();
        inactiveTicket.setVehicle(vehicle);
        inactiveTicket.setEntryCancel(2);
        inactiveTicket.setDateTimeEntry(LocalDateTime.now().minusDays(1));
        inactiveTicket.setDateTimeExit(LocalDateTime.now());
        inactiveTicket.setParked(false);

        entityManager.persist(inactiveTicket);
    }

    @Test
    public void findByVehicle_Plate_WithValidData_ReturnTicketList() {
        List<Ticket> foundTickets = ticketRepository.findByVehicle_Plate("ABC-1234");

        assertNotNull(foundTickets);
        assertFalse(foundTickets.isEmpty());

        assertEquals(2, foundTickets.size());

        Ticket activeTicket = foundTickets.stream().filter(Ticket::getParked).findFirst().orElse(null);
        assertNotNull(activeTicket);
        assertTrue(activeTicket.getParked());
        assertEquals("ABC-1234", activeTicket.getVehicle().getPlate());

        Ticket inactiveTicket = foundTickets.stream().filter(t -> !t.getParked()).findFirst().orElse(null);
        assertNotNull(inactiveTicket);
        assertFalse(inactiveTicket.getParked());
        assertEquals("ABC-1234", inactiveTicket.getVehicle().getPlate());
    }

    @Test
    public void findByVehicle_Plate_WithInvalidData_ReturnErrorMessage() {
        List<Ticket> foundTickets = ticketRepository.findByVehicle_Plate("INVALID_PLATE");

        assertTrue(foundTickets.isEmpty());
    }

    @Test
    public void findActiveTicketByVehiclePlate_WithValidData_ReturnTicket() {
        Ticket foundTicket = ticketRepository.findActiveTicketByVehiclePlate("ABC-1234");

        assertNotNull(foundTicket);
        assertEquals("ABC-1234", foundTicket.getVehicle().getPlate());
        assertTrue(foundTicket.getParked());
        assertNull(foundTicket.getDateTimeExit());
    }

    @Test
    public void findActiveTicketByVehiclePlate_WithInvalidData_ReturnErrorMessage() {
        Ticket foundTicket = ticketRepository.findActiveTicketByVehiclePlate("INVALID_PLATE");

        assertNull(foundTicket);
    }
}