package com.spring.roommgmt.controller.dto;

import com.spring.roommgmt.model.Building;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Building.
 * Used for transferring building data between client and server.
 *
 * @param id the unique identifier of the building
 * @param buildingNumber the unique building number
 * @param description the description of the building
 * @param publicAccess indicates if the building is publicly accessible
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public record BuildingDTO(
        Long id,
        @NotBlank @Size(max = 50) String buildingNumber,
        @Size(max = 255) String description,
        boolean publicAccess
) {

    /**
     * Converts a Building entity to a BuildingDTO.
     *
     * @param building the Building entity to convert
     * @return the converted BuildingDTO
     */
    public static BuildingDTO fromBuilding(Building building) {
        return new BuildingDTO(
                building.getId(),
                building.getBuildingNumber(),
                building.getDescription(),
                building.isPublicAccess()
        );
    }

    /**
     * Converts this BuildingDTO to a Building entity.
     *
     * @return the converted Building entity
     */
    public Building toBuilding() {
        return new Building(id, buildingNumber, description, publicAccess);
    }

}
