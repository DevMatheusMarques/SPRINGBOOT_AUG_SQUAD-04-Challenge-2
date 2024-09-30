package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleResponseDto;

import java.util.List;

/**
 * A utility class that maps Vehicle entities to DTOs (Data Transfer Objects) and vice versa.
 * This helps in converting the Vehicle entity to a format suitable for API responses and vice versa.
 */
public class VehicleMapper {

    /**
     * Converts a VehicleCreateDto object to a Vehicle entity.
     *
     * @param createDto The DTO containing the vehicle creation details.
     * @return A new Vehicle entity based on the provided DTO.
     */
    public static Vehicle toVehicle(VehicleCreateDto createDto) {
        return new Vehicle(createDto.getPlate(),
                Enum.valueOf(TypeVehicle.class, createDto.getTypeVehicle()),
                Enum.valueOf(Category.class, createDto.getCategory()));
    }

    /**
     * Converts a list of Vehicle entities to a list of VehicleResponseDto objects.
     *
     * @param listDto The list of Vehicle entities to be converted.
     * @return A list of VehicleResponseDto objects representing the vehicles.
     */
    public static List<VehicleResponseDto> toListDto(List<Vehicle> listDto){
        return listDto.stream().map(VehicleMapper::toDto).toList();
    }

    /**
     * Converts a Vehicle entity to a VehicleResponseDto object.
     *
     * @param vehicle The Vehicle entity to be converted.
     * @return A VehicleResponseDto object representing the vehicle.
     */
    public static VehicleResponseDto toDto(Vehicle vehicle) {
        return new VehicleResponseDto(vehicle.getId(), vehicle.getPlate(), vehicle.getTypeVehicle().toString(), vehicle.getCategory().toString(), vehicle.getRegistered());
    }
}
