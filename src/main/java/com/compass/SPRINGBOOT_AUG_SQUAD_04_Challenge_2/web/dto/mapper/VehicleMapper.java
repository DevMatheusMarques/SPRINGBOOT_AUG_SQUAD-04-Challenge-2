package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.Category;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.enums.TypeVehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleResponseDto;


public class VehicleMapper {

    //Gets DTO and returns a new Vehicle object with DTO attributes
    public static Vehicle toVehicle(VehicleCreateDto createDto) {
        return new Vehicle(createDto.getPlate(),
                   Enum.valueOf(TypeVehicle.class ,createDto.getType()),
                   Enum.valueOf(Category.class ,createDto.getCategory()));
    }

    public static VehicleResponseDto toDto(Vehicle vehicle) {
        return new VehicleResponseDto(vehicle.getId(), vehicle.getPlate(), vehicle.getTypeVehicle().toString(), vehicle.getCategory().toString(),vehicle.getRegistered());
    }
}
