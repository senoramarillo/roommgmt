package com.spring.roommgmt.model;

public class BuildingTestDataBuilder {
    private Long id = 1L;
    private String buildingNumber = "Test";
    private String description = "Test Building";
    private boolean publicAccess = true;

    public BuildingTestDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BuildingTestDataBuilder withBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    public BuildingTestDataBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public BuildingTestDataBuilder withPublicAccess(boolean publicAccess) {
        this.publicAccess = publicAccess;
        return this;
    }

    public Building build() {
        return new Building(id, buildingNumber, description, publicAccess);
    }

}
