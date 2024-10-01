package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Ticket;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidEntryCancelException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.InvalidExitCancelException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.VehicleInParkingException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private ParkingService parkingService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private Ticket validTicket;

    @BeforeEach
    public void setUp() {
        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setPlate("XYZ-1234");
        vehicle.setCategory(Category.MONTHLY_PAYER);
        vehicle.setTypeVehicle(TypeVehicle.PASSENGER_CAR);
        vehicle.setRegistered(true);

        validTicket = new Ticket();
        validTicket.setId(1L);
        validTicket.setVehicle(vehicle);
        validTicket.setEntryCancel(1);
        validTicket.setDateTimeEntry(LocalDateTime.now());
        validTicket.setParked(true);
        validTicket.setInitialVacancyOccupied(0);
        validTicket.setVacanciesOccupied(new LinkedList<>(Arrays.asList(0, 1)));
    }

    @Test
    public void saveTicket_WithValidData_ReturnTicket() {
        List<Integer> vacancyListForMonthly = Arrays.asList(0, 1);
        List<Integer> vacancyListForSingle = Arrays.asList(2, 3);

        lenient().when(parkingService.vehicleEntry(Category.MONTHLY_PAYER, TypeVehicle.PASSENGER_CAR)).thenReturn(vacancyListForMonthly);
        lenient().when(parkingService.vehicleEntry(Category.SINGLE, TypeVehicle.PASSENGER_CAR)).thenReturn(vacancyListForSingle);

        when(ticketRepository.save(validTicket)).thenReturn(validTicket);

        Ticket sut = ticketService.saveTicket(validTicket);

        assertThat(sut).isEqualTo(validTicket);
    }

    @Test
    public void saveTicket_WithInvalidData_ThrowsVehicleInParkingException() {
        String plate = validTicket.getVehicle().getPlate();
        Ticket activeTicket = new Ticket();
        activeTicket.setVehicle(validTicket.getVehicle());

        when(ticketService.findActiveTicketByVehiclePlate(plate)).thenReturn(activeTicket);

        assertThatThrownBy(() -> ticketService.saveTicket(validTicket))
                .isInstanceOf(VehicleInParkingException.class)
                .hasMessage("This vehicle is already in the parking lot.");
    }

    @Test
    public void saveTicket_WithInvalidEntryCancel_ThrowsInvalidEntryCancelException() {
        validTicket.setEntryCancel(7);

        assertThatThrownBy(() -> ticketService.saveTicket(validTicket)).isInstanceOf(InvalidEntryCancelException.class);
    }

    @Test
    public void getTickets_ByExistingPlate_ReturnListTickets() {
        List<Ticket> tickets = List.of(validTicket);

        when(ticketRepository.findByVehicle_Plate(validTicket.getVehicle().getPlate())).thenReturn(tickets);

        List<Ticket> sut = ticketService.findTicketsByPlate(validTicket.getVehicle().getPlate());

        assertThat(sut).isNotEmpty();
        assertThat(sut.getFirst()).isEqualTo(validTicket);
    }

    @Test
    public void getTickets_ByUnexistingPlate_ThrowsNoResultsFoundException() {
        final String plate = "XXX-1111";

        assertThatThrownBy(() -> ticketService.findTicketsByPlate(plate)).isInstanceOf(NoResultsFoundException.class);
    }

    @Test
    public void getTicket_ByExistingId_ReturnTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(validTicket));

        Optional<Ticket> sut = Optional.ofNullable(ticketService.findTicketById(1L));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(validTicket);
    }

    @Test
    public void getTicket_ByUnexistingId_ThrowsNoResultsFoundException() {
        assertThatThrownBy(() -> ticketService.findTicketById(99L)).isInstanceOf(NoResultsFoundException.class);
    }

    @Test
    public void getAllTickets_ReturnListTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(validTicket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> sut = ticketService.findAllTickets();

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.getFirst()).isEqualTo(validTicket);
    }

    @Test
    public void getAllTickets_ReturnEmptyList() {
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        List<Ticket> sut = ticketService.findAllTickets();

        assertThat(sut).isEmpty();
    }

    // Analisar melhor
    @Test
    public void getTicketsByFilter_WithNoFilter_ReturnAllTickets() {
        List<Ticket> tickets = Collections.singletonList(validTicket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.getTickets(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(validTicket, result.getFirst());
    }

    @Test
    public void getTicketsByFilter_WithValidIdFilter_ReturnTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.ofNullable(validTicket));

        List<Ticket> result = ticketService.getTickets("id", "1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(validTicket, result.getFirst());
    }

    // Analisar melhor
    @Test
    public void getTicketsByFilter_WithValidPlateFilter_ReturnTickets() {
        when(ticketRepository.findByVehicle_Plate("XYZ-1234")).thenReturn(Collections.singletonList(validTicket));

        List<Ticket> result = ticketService.getTickets("plate", "XYZ-1234");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(validTicket, result.getFirst());
    }

    @Test
    public void getTicketsByFilter_WithInvalidFilterType_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ticketService.getTickets("invalid", "value"));
        assertEquals("Invalid filter type", exception.getMessage());
    }

    @Test
    public void getTicketsByFilter_WithInvalidIdFilter_ThrowsNoResultsFoundException() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.getTickets("id", "999"))
                .isInstanceOf(NoResultsFoundException.class)
                .hasMessageContaining("Ticket not found");
    }

    @Test
    public void updateTicket_WithValidData_ReturnTicket() {
        when(ticketRepository.findById(validTicket.getId())).thenReturn(Optional.of(validTicket));
        when(ticketService.findActiveTicketByVehiclePlate(validTicket.getVehicle().getPlate())).thenReturn(validTicket);

        validTicket.setExitCancel(8);
        validTicket.setParked(false);

        assertDoesNotThrow(() -> {
            ticketService.updateTicket(validTicket.getId(), validTicket);
        });

        verify(ticketRepository).save(validTicket);
    }

    @Test
    public void updateTicket_WithInactiveTicket_ThrowsIllegalStateException() {
        validTicket.setExitCancel(8);
        validTicket.setDateTimeExit(LocalDateTime.now());
        validTicket.setParked(false);

        Ticket activeTicket = new Ticket();
        activeTicket.setId(2L);
        activeTicket.setVehicle(validTicket.getVehicle());
        activeTicket.setParked(true);

        when(ticketRepository.findById(validTicket.getId())).thenReturn(Optional.of(validTicket));
        when(ticketService.findActiveTicketByVehiclePlate(validTicket.getVehicle().getPlate())).thenReturn(activeTicket);

        assertThrows(IllegalStateException.class, () -> {
            ticketService.updateTicket(validTicket.getId(), validTicket);
        });
    }

    @Test
    public void updateTicket_WithInvalidExitCancel_ThrowsInvalidExitCancelException() {
        validTicket.setExitCancel(11);

        when(ticketRepository.findById(validTicket.getId())).thenReturn(Optional.of(validTicket));
        when(ticketService.findActiveTicketByVehiclePlate(validTicket.getVehicle().getPlate())).thenReturn(validTicket);

        assertThatThrownBy(() -> ticketService.updateTicket(1L, validTicket)).isInstanceOf(InvalidExitCancelException.class);
    }

    @Test
    public void findActiveTicket_ByExistingPlate_ReturnTicket() {
        when(ticketRepository.findActiveTicketByVehiclePlate(validTicket.getVehicle().getPlate())).thenReturn(validTicket);

        Ticket foundTicket = ticketService.findActiveTicketByVehiclePlate(validTicket.getVehicle().getPlate());

        assertNotNull(foundTicket);
        assertEquals(validTicket, foundTicket);
    }

    @Test
    public void findActiveTicket_ByUnexistingPlate_ReturnNull() {
        when(ticketRepository.findActiveTicketByVehiclePlate("INVALID_PLATE")).thenReturn(null);

        Ticket foundTicket = ticketService.findActiveTicketByVehiclePlate("INVALID_PLATE");

        assertNull(foundTicket);
    }

    @Test
    public void deleteTicket_WithExistingId_DoesNotThrowAnyException() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(validTicket));
        assertThatCode(() -> ticketService.deleteTicket(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteTicket_WithUnexistingId_ThrowsNoResultsFoundException() {
        assertThatThrownBy(() -> ticketService.deleteTicket(99L)).isInstanceOf(NoResultsFoundException.class);
    }

}
