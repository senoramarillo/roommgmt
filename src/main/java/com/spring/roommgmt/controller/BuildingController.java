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

@RestController()
@AllArgsConstructor
@RequestMapping("buildings")
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<List<BuildingDTO>> findBuildings() {
        return ResponseEntity.ok(buildingService.findAll().stream().map(BuildingDTO::fromBuilding).toList());
    }

    @GetMapping("{buildingNumber}")
    public ResponseEntity<BuildingDTO> findByBuildingNumber(@PathVariable String buildingNumber) {
        var buildingFound = buildingService.findByBuildingNumber(buildingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        return ResponseEntity.ok(fromBuilding(buildingFound));
    }

    @GetMapping("public")
    public List<BuildingDTO> findPublicBuildings() {
        return buildingService.findPublic().stream().map(BuildingDTO::fromBuilding).toList();
    }

    @PostMapping
    public ResponseEntity<BuildingDTO> createNew(@RequestBody BuildingDTO building) {
        var newBuilding = buildingService.createNew(building.toBuilding());
        return ResponseEntity.status(CREATED).body(fromBuilding(newBuilding));
    }

    @PutMapping("{buildingNumber}")
    public ResponseEntity<BuildingDTO> update(@PathVariable String buildingNumber, @RequestBody BuildingDTO building) {
        return ResponseEntity.ok(fromBuilding(buildingService.update(buildingNumber, building.toBuilding())));
    }

    @DeleteMapping("{buildingNumber}")
    public ResponseEntity<BuildingDTO> delete(@PathVariable String buildingNumber) {
        buildingService.delete(buildingNumber);
        return ResponseEntity.ok().build();
    }

}
