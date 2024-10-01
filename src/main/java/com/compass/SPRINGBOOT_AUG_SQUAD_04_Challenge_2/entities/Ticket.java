package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Entity representing a parking ticket.
 * This class contains all the necessary information related to a vehicle's parking session.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    /**
     * Unique identifier for the ticket.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Indicates whether the vehicle is currently parked.
     */
    @Column(nullable = false)
    private Boolean parked;

    /**
     * Timestamp for when the vehicle entered the parking facility.
     */
    @Column(name = "datetime_entry", nullable = false)
    private LocalDateTime dateTimeEntry;

    /**
     * Timestamp for when the vehicle exited the parking facility.
     */
    @Column(name = "datetime_exit")
    private LocalDateTime dateTimeExit;

    /**
     * Entry cancel number associated with the ticket.
     */
    @Column(name = "entry_cancel", length = 2, nullable = false)
    private Integer entryCancel;

    /**
     * Exit cancel number associated with the ticket.
     */
    @Column(name = "exit_cancel", length = 2)
    private Integer exitCancel;

    /**
     * Final price for the parking session.
     */
    @Column(name = "final_price")
    private Double finalPrice;

    /**
     * Number of initial vacancies occupied by the vehicle.
     */
    @Column(name = "primary_vacancies_occupied")
    private Integer initialVacancyOccupied;

    /**
     * List of vacancies occupied during the parking session.
     */
    @Column(name = "vacancies_occupied")
    @ElementCollection
    private List<Integer> vacanciesOccupied;

    /**
     * The vehicle associated with this ticket.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_plate", referencedColumnName = "plate", nullable = false)
    private Vehicle vehicle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
