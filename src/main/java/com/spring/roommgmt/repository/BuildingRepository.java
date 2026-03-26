package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Building entities.
 * Provides database access operations for buildings using Spring Data JPA.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {

    /**
     * Finds a building by its unique building number.
     *
     * @param buildingNumber the building number to search for
     * @return Optional containing the found building
     */
    Optional<Building> findByBuildingNumber(String buildingNumber);

    /**
     * Finds all buildings that are publicly accessible.
     *
     * @return List of all public buildings
     */
    List<Building> findByPublicAccessIsTrue();

}