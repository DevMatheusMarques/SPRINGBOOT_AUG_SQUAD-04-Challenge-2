package com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.controller;

import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.entities.Vehicle;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.exceptions.NoResultsFoundException;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.services.VehicleService;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleCreateDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.VehicleResponseDto;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.dto.mapper.VehicleMapper;
import com.compass.SPRINGBOOT_AUG_SQUAD_04_Challenge_2.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Vehicles", description = "Contains all operations related to resources for registering, editing and reading a vehicle.")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Register new vehicle.", description = "Resource to register a new vehicle.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vehicle registered successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Unable to register vehicle due to invalid field or public service.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@RequestBody VehicleCreateDto createDto) {
        Vehicle vehicle = VehicleMapper.toVehicle(createDto);
        vehicleService.saveVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(VehicleMapper.toDto(vehicle));
    }
    @Operation(summary = "Search for a vehicle by license plate.", description = "Returns the vehicle that contains the license plate provided.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle found successfully.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = VehicleResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponseDto> getByPlate(@PathVariable String plate) {
        Vehicle vehicle = vehicleService.findVehicleByPlate(plate);
        return ResponseEntity.status(HttpStatus.OK).body(VehicleMapper.toDto(vehicle));
    }

    @Operation(summary = "List all registered vehicles.", description = "Returns all registered vehicles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicles found successfully.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = VehicleResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAll() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        if(vehicles.isEmpty()) {throw new NoResultsFoundException("No vehicles are registered yet");
        }
        return ResponseEntity.ok(VehicleMapper.toListDto(vehicles));
    }

    @Operation(summary = "Update vehicle data by ID.", description = "Update vehicle data with new information.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Vehicle updated successfully.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = VehicleResponseDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Public service vehicle cannot be paid monthly.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "ID not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> update(@PathVariable Long id, @RequestBody VehicleCreateDto vehicles) {
        Vehicle vehicle = VehicleMapper.toVehicle(vehicles);
        vehicleService.updateVehicle(id, vehicle);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete vehicle by ID.", description = "Delete vehicle from database by ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Vehicle successfully deleted.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = VehicleResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "ID not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
