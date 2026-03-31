package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Building;
import com.spring.roommgmt.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Room entities.
 * Provides database access operations for rooms using Spring Data JPA.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override
    @EntityGraph(attributePaths = "building")
    List<Room> findAll();

    /**
     * Finds a room by its building and room number.
     *
     * @param building the building entity containing the room
     * @param roomNumber the room number to search for
     * @return Optional containing the found room
     */
    @EntityGraph(attributePaths = "building")
    Optional<Room> findByBuildingAndRoomNumber(Building building, String roomNumber);

    /**
     * Finds all rooms in a specific building.
     *
     * @param building the building entity
     * @return List of rooms in the specified building
     */
    @EntityGraph(attributePaths = "building")
    List<Room> findByBuilding(Building building);

    @EntityGraph(attributePaths = "building")
    List<Room> findByBuildingAndFloorOrderByRoomNumber(Building building, String floor);

    boolean existsByBuilding(Building building);

    /**
     * Finds all rooms in publicly accessible buildings.
     * Uses a custom JPQL query to join with building entities.
     *
     * @return List of all public rooms
     */
    @EntityGraph(attributePaths = "building")
    @Query("select room from Room room join room.building building where building.publicAccess = true")
    List<Room> findPublicRooms();

}
