package com.spring.roommgmt.service.implementation;

import com.spring.roommgmt.model.Building;
import com.spring.roommgmt.model.Room;
import com.spring.roommgmt.repository.RoomRepository;
import com.spring.roommgmt.service.BuildingService;
import com.spring.roommgmt.service.RoomService;
import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for Room Management.
 * Provides concrete implementations of room business logic.
 * Handles creation, updating, deletion, and retrieval of rooms.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final BuildingService buildingService;

    /**
     * Retrieves all rooms from the repository.
     *
     * @return List of all rooms
     */
    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    /**
     * Finds a room by its building number and room number.
     *
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return Optional containing the found room
     * @throws ResourceNotFoundException if the building is not found
     */
    @Override
    public Optional<Room> findByBuildingNumberAndRoomNumber(String buildingNumber, String roomNumber) {
        var building = findBuildingOrThrow(buildingNumber);
        return roomRepository.findByBuildingAndRoomNumber(building, roomNumber);
    }

    /**
     * Retrieves all rooms in a specific building.
     *
     * @param buildingNumber the building number
     * @return List of rooms in the specified building
     * @throws ResourceNotFoundException if the building is not found
     */
    @Override
    public List<Room> findByBuildingNumber(String buildingNumber) {
        var building = findBuildingOrThrow(buildingNumber);
        return roomRepository.findByBuilding(building);
    }

    /**
     * Retrieves all publicly accessible rooms.
     *
     * @return List of all public rooms
     */
    @Override
    public List<Room> findPublic() {
        return roomRepository.findPublicRooms();
    }

    /**
     * Creates a new room in a specific building, ensuring the room number is unique within the building.
     *
     * @param buildingNumber the building number where the room is created
     * @param room the new room to create
     * @return the created room with generated ID
     * @throws ResourceNotFoundException if the building is not found
     * @throws DuplicateKeyException if a room with the same number already exists in the building
     */
    @Override
    public Room createNew(String buildingNumber, Room room) {
        var building = findBuildingOrThrow(buildingNumber);
        if (findByBuildingNumberAndRoomNumber(building.getBuildingNumber(), room.getRoomNumber()).isPresent()) {
            throw new DuplicateKeyException();
        }
        room.setBuilding(building);
        return roomRepository.save(room);
    }

    /**
     * Updates an existing room.
     *
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number to update
     * @param room the updated room data
     * @return the updated room
     * @throws IllegalArgumentException if room numbers do not match
     * @throws ResourceNotFoundException if the building or room is not found
     */
    @Override
    public Room update(String buildingNumber, String roomNumber, Room room) {
        if (!roomNumber.equals(room.getRoomNumber())) {
            throw new IllegalArgumentException();
        }
        var roomToBeUpdated = findRoomOrThrow(buildingNumber, roomNumber);
        roomToBeUpdated.setSeats(room.getSeats());
        roomToBeUpdated.setProjectorPresent(room.isProjectorPresent());

        return roomToBeUpdated;
    }

    /**
     * Deletes a room.
     *
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number to delete
     * @throws ResourceNotFoundException if the building or room is not found
     */
    @Override
    public void delete(String buildingNumber, String roomNumber) {
        var roomToBeDeleted = findRoomOrThrow(buildingNumber, roomNumber);
        roomRepository.delete(roomToBeDeleted);
    }

    /**
     * Helper method to find a building by its number or throw an exception.
     *
     * @param buildingNumber the building number to search for
     * @return the found building
     * @throws ResourceNotFoundException if the building is not found
     */
    private Building findBuildingOrThrow(String buildingNumber) {
        return buildingService.findByBuildingNumber(buildingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
    }

    /**
     * Helper method to find a room by its building and room number or throw an exception.
     *
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return the found room
     * @throws ResourceNotFoundException if the room is not found
     */
    private Room findRoomOrThrow(String buildingNumber, String roomNumber) {
        return findByBuildingNumberAndRoomNumber(buildingNumber, roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

}