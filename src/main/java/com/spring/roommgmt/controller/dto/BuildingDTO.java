package com.spring.roommgmt.controller.dto;

import com.spring.roommgmt.model.Building;

public record BuildingDTO(Long id, String buildingNumber, String description, boolean publicAccess) {

    public static BuildingDTO fromBuilding(Building building) {
        return new BuildingDTO(
                building.getId(),
                building.getBuildingNumber(),
                building.getDescription(),
                building.isPublicAccess()
        );
    }

    public Building toBuilding() {
        return new Building(id, buildingNumber, description, publicAccess);
    }

}
