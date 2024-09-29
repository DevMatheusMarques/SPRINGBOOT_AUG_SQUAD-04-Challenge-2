package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "registered", nullable = false)
    private Boolean registered;
    @Column(name = "plate", nullable = false, length = 9, unique = true)
    private String plate;
    @Column(name = "category", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name = "type_vehicle", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TypeVehicle typeVehicle;

    @CreatedDate
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @LastModifiedDate
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<Ticket> ticket;

    //Constructor for DTO
    public Vehicle(String plate, TypeVehicle typeVehicle, Category category) {
        this.plate = plate;
        this.typeVehicle = typeVehicle;
        this.category = category;
    }
}