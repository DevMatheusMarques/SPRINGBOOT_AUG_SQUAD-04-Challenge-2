package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "vehicles")
@Entity
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plate", nullable = false, length = 9)
    private String plate;
    @Column(name = "type", nullable = false, length = 20)
    private TypeVehicle type;
    @Column(name = "monthly", nullable = false)
    private Boolean monthly;

    @CreatedDate
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @LastModifiedDate
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    @OneToOne
    @JoinColumn(name = "vacany.id", referencedColumnName = "id")
    private Vacancy vacancy;

    @OneToOne(mappedBy = "vehicle")
    private Ticket ticket;
}