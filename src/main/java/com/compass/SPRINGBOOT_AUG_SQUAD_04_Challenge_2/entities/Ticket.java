package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.ParkingService;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private Boolean parked;
    @Column(name = "datetime_entry", nullable = false)
    private LocalDateTime dateTimeEntry;
    @Column(name = "datetime_exit")
    private LocalDateTime dateTimeExit;
    @Column(name = "entry_cancel", length = 2, nullable = false)
    private Integer entryCancel;
    @Column(name = "exit_cancel", length = 2)
    private Integer exitCancel;
    @Column(name = "final_price")
    private Double finalPrice;
    @Column(name = "vacancies_occupied")
    private Integer vacanciesOccupied;

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
