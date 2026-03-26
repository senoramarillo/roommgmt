package com.spring.roommgmt.service;

import com.spring.roommgmt.model.Building;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for Building Management.
 * Defines business logic for managing buildings.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface BuildingService {

    /**
     * Retrieves all buildings.
     *
     * @return List of all buildings
     */
    List<Building> findAll();

    /**
     * Finds a building by its building number.
     *
     * @param buildingNumber the building number
     * @return Optional containing the found building
     */
    Optional<Building> findByBuildingNumber(String buildingNumber);

    /**
     * Finds all publicly accessible buildings.
     *
     * @return List of all public buildings
     */
    List<Building> findPublic();

    /**
     * Creates a new building.
     *
     * @param building the new building
     * @return the created building with generated ID
     */
    Building createNew(Building building);

    /**
     * Updates an existing building.
     *
     * @param buildingNumber the building number of the building to update
     * @param building the updated data
     * @return the updated building
     */
    Building update(String buildingNumber, Building building);

    /**
     * Deletes a building by its building number.
     *
     * @param buildingNumber the building number of the building to delete
     */
    void delete(String buildingNumber);

    /**
     * Deletes a building by its ID.
     *
     * @param buildingId the ID of the building to delete
     */
    void deleteById(Long buildingId);

}
