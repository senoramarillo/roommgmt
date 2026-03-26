package com.spring.roommgmt.service;

import com.spring.roommgmt.model.Room;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for Room Management.
 * Defines business logic for managing rooms within buildings.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface RoomService {

    /**
     * Retrieves all rooms.
     *
     * @return List of all rooms
     */
    List<Room> findAll();

    /**
     * Finds a room by its building number and room number.
     *
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return Optional containing the found room
     */
    Optional<Room> findByBuildingNumberAndRoomNumber(String buildingNumber, String roomNumber);

    /**
     * Retrieves all rooms in a specific building.
     *
     * @param buildingNumber the building number
     * @return List of rooms in the specified building
     */
    List<Room> findByBuildingNumber(String buildingNumber);

    /**
     * Finds all publicly accessible rooms.
     *
     * @return List of public rooms
     */
    List<Room> findPublic();

    /**
     * Creates a new room in a specific building.
     *
     * @param buildingNumber the building number where the room is created
     * @param room the new room
     * @return the created room with generated ID
     */
    Room createNew(String buildingNumber, Room room);

    /**
     * Updates an existing room.
     *
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number to update
     * @param room the updated room data
     * @return the updated room
     */
    Room update(String buildingNumber, String roomNumber, Room room);

    /**
     * Deletes a room.
     *
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number to delete
     */
    void delete(String buildingNumber, String roomNumber);
}
