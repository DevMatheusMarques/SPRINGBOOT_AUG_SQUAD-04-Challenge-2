package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @Column(name = "entry_gate", length = 2, nullable = false)
    private Integer entryGate;
    @Column(name = "exit_gate", length = 2)
    private Integer exitGate;
    @Column(name = "final_price")
    private Double finalPrice;

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
