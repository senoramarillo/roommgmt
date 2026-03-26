package com.spring.roommgmt.service.implementation;

import com.spring.roommgmt.model.Building;
import com.spring.roommgmt.repository.BuildingRepository;
import com.spring.roommgmt.service.BuildingService;
import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for Building Management.
 * Provides concrete implementations of building business logic.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;

    /**
     * Retrieves all buildings from the repository.
     *
     * @return List of all buildings
     */
    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    /**
     * Finds a building by its building number.
     *
     * @param buildingNumber the building number to search for
     * @return Optional containing the found building
     */
    @Override
    public Optional<Building> findByBuildingNumber(String buildingNumber) {
        return buildingRepository.findByBuildingNumber(buildingNumber);
    }

    /**
     * Retrieves all publicly accessible buildings.
     *
     * @return List of public buildings
     */
    @Override
    public List<Building> findPublic() {
        return buildingRepository.findByPublicAccessIsTrue();
    }

    /**
     * Creates a new building, ensuring the building number is unique.
     *
     * @param building the new building to create
     * @return the created building with generated ID
     * @throws DuplicateKeyException if a building with the same number already exists
     */
    @Override
    public Building createNew(Building building) {
        if (buildingRepository.findByBuildingNumber(building.getBuildingNumber()).isPresent()) {
            throw new DuplicateKeyException();
        }
        return buildingRepository.save(building);
    }

    /**
     * Updates an existing building.
     *
     * @param buildingNumber the building number to update
     * @param building the updated building data
     * @return the updated building
     * @throws IllegalArgumentException if building numbers do not match
     * @throws ResourceNotFoundException if the building is not found
     */
    @Override
    public Building update(String buildingNumber, Building building) {
        if (!buildingNumber.equals(building.getBuildingNumber())) {
            throw new IllegalArgumentException();
        }
        var buildingToBeUpdated = findBuildingOrThrow(buildingNumber);
        buildingToBeUpdated.setDescription(building.getDescription());
        buildingToBeUpdated.setPublicAccess(building.isPublicAccess());
        return buildingToBeUpdated;
    }

    /**
     * Deletes a building by its building number.
     *
     * @param buildingNumber the building number to delete
     * @throws ResourceNotFoundException if the building is not found
     */
    @Override
    public void delete(String buildingNumber) {
        var buildingToBeDeleted = findBuildingOrThrow(buildingNumber);
        buildingRepository.delete(buildingToBeDeleted);
    }

    /**
     * Deletes a building by its ID.
     *
     * @param buildingId the ID of the building to delete
     */
    @Override
    public void deleteById(Long buildingId) {
        buildingRepository.deleteById(buildingId);
    }

    /**
     * Helper method to find a building by its building number or throw an exception.
     *
     * @param buildingNumber the building number to search for
     * @return the found building
     * @throws ResourceNotFoundException if the building is not found
     */
    private Building findBuildingOrThrow(String buildingNumber) {
        return findByBuildingNumber(buildingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
    }

}
