package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VehicleService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@RequestBody VehicleCreateDto createDto) {
        Vehicle vehicle = VehicleMapper.toVehicle(createDto);
        vehicleService.saveVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(VehicleMapper.toDto(vehicle));
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponseDto> getByPlate(@PathVariable String plate) {
        Vehicle vehicle = vehicleService.findVehicleByPlate(plate);
        return ResponseEntity.status(HttpStatus.OK).body(VehicleMapper.toDto(vehicle));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAll() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        return ResponseEntity.ok(VehicleMapper.toListDto(vehicles));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        Vehicle vehicleDto = vehicleService.updateVehicle(id, vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(VehicleMapper.toDto(vehicleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
