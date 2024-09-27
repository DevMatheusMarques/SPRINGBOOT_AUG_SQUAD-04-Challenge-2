package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle; // Importe o enum
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "vacancy")
@Entity
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "separated_occupied", nullable = false)
    private Integer separated_ocuppied;

    @Column(name = "separated_capacity", nullable = false)
    private Integer separated_capacity;

    @Column(name = "monthly_occupied", nullable = false)
    private Integer monthly_occupied;

    @Column(name = "monthly_capacity", nullable = false)
    private Integer monthly_capacity;
}
