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

/**
 * The `Vehicle` class represents a vehicle in the system.
 * This class is a JPA entity and maps to the "vehicles" table in the database.
 * It includes information such as ID, plate number, category, and vehicle type,
 * as well as metadata for creation and modification timestamps.
 */
@Getter // Automatically generates getter methods for the attributes.
@Setter // Automatically generates setter methods for the attributes.
@NoArgsConstructor // Automatically generates a no-argument constructor.
@AllArgsConstructor // Automatically generates an all-arguments constructor.
@EqualsAndHashCode // Automatically generates equals and hashCode methods.
@Table(name = "vehicles") // Specifies the table name in the database.
@Entity // Specifies that this class is a JPA entity.
public class Vehicle implements Serializable {

    /**
     * The unique identifier for the vehicle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Indicates if the vehicle is registered.
     */
    @Column(name = "registered", nullable = false)
    private Boolean registered = false;

    /**
     * The vehicle's license plate. It must be unique and have a maximum length of 9 characters.
     */
    @Column(name = "plate", nullable = false, length = 9, unique = true)
    private String plate;

    /**
     * The category of the vehicle, defined by the `Category` enum.
     */
    @Column(name = "category", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Category category;

    /**
     * The type of the vehicle, defined by the `TypeVehicle` enum.
     */
    @Column(name = "type_vehicle", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TypeVehicle typeVehicle;

    /**
     * The date when the vehicle record was created.
     * Managed automatically by Spring Data.
     */
    @CreatedDate
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    /**
     * The date when the vehicle record was last modified.
     * Managed automatically by Spring Data.
     */
    @LastModifiedDate
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    /**
     * List of tickets associated with the vehicle.
     * The `JsonIgnore` annotation ensures this field is ignored in JSON serialization.
     */
    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<Ticket> ticket;

    /**
     * Constructor for creating a vehicle instance using only plate, typeVehicle, and category.
     * This constructor is used in DTOs (Data Transfer Objects).
     *
     * @param plate       The vehicle's license plate.
     * @param typeVehicle The type of the vehicle.
     * @param category    The category of the vehicle.
     */
    public Vehicle(String plate, TypeVehicle typeVehicle, Category category) {
        this.plate = plate;
        this.typeVehicle = typeVehicle;
        this.category = category;
    }
}
