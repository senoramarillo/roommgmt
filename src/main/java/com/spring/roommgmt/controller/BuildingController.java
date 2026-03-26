package com.spring.roommgmt.controller;

import com.spring.roommgmt.controller.dto.BuildingDTO;
import com.spring.roommgmt.service.BuildingService;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.spring.roommgmt.controller.dto.BuildingDTO.fromBuilding;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST Controller for Building Management.
 * Provides endpoints for retrieving, creating, updating, and deleting buildings.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@RestController()
@AllArgsConstructor
@RequestMapping("buildings")
public class BuildingController {

    private final BuildingService buildingService;

    /**
     * Retrieves all buildings.
     *
     * @return ResponseEntity containing a list of all buildings
     */
    @GetMapping
    public ResponseEntity<List<BuildingDTO>> findBuildings() {
        return ResponseEntity.ok(buildingService.findAll().stream().map(BuildingDTO::fromBuilding).toList());
    }

    /**
     * Finds a building by its building number.
     *
     * @param buildingNumber the building number to search for
     * @return ResponseEntity containing the found building
     * @throws ResourceNotFoundException if the building is not found
     */
    @GetMapping("{buildingNumber}")
    public ResponseEntity<BuildingDTO> findByBuildingNumber(@PathVariable String buildingNumber) {
        var buildingFound = buildingService.findByBuildingNumber(buildingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        return ResponseEntity.ok(fromBuilding(buildingFound));
    }

    /**
     * Retrieves all publicly accessible buildings.
     *
     * @return List of all buildings with public access
     */
    @GetMapping("public")
    public List<BuildingDTO> findPublicBuildings() {
        return buildingService.findPublic().stream().map(BuildingDTO::fromBuilding).toList();
    }

    /**
     * Creates a new building.
     *
     * @param building the new building as DTO
     * @return ResponseEntity containing the created building (HTTP Status 201 CREATED)
     */
    @PostMapping
    public ResponseEntity<BuildingDTO> createNew(@RequestBody BuildingDTO building) {
        var newBuilding = buildingService.createNew(building.toBuilding());
        return ResponseEntity.status(CREATED).body(fromBuilding(newBuilding));
    }

    /**
     * Updates an existing building.
     *
     * @param buildingNumber the building number of the building to update
     * @param building the updated building data as DTO
     * @return ResponseEntity containing the updated building
     */
    @PutMapping("{buildingNumber}")
    public ResponseEntity<BuildingDTO> update(@PathVariable String buildingNumber, @RequestBody BuildingDTO building) {
        return ResponseEntity.ok(fromBuilding(buildingService.update(buildingNumber, building.toBuilding())));
    }

    /**
     * Deletes a building by its ID.
     *
     * @param buildingId the ID of the building to delete
     * @return ResponseEntity with HTTP Status 200 OK
     */
    @DeleteMapping("{buildingId}")
    public ResponseEntity<BuildingDTO> delete(@PathVariable Long buildingId) {
        buildingService.deleteById(buildingId);
        return ResponseEntity.ok().build();
    }

}
