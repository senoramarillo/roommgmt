package com.spring.roommgmt.controller;

import com.spring.roommgmt.controller.dto.RoomDTO;
import com.spring.roommgmt.service.RoomService;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.spring.roommgmt.controller.dto.RoomDTO.fromRoom;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST Controller for Room Management.
 * Provides endpoints for retrieving, creating, updating, and deleting rooms.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * Retrieves all rooms.
     *
     * @return List of all rooms
     */
    @GetMapping("rooms")
    public List<RoomDTO> findAll() {
        return roomService.findAll().stream().map(RoomDTO::fromRoom).toList();
    }

    /**
     * Retrieves all publicly accessible rooms.
     *
     * @return List of all public rooms
     */
    @GetMapping("rooms/public")
    public List<RoomDTO> findByBuilding() {
        return roomService.findPublic().stream().map(RoomDTO::fromRoom).toList();
    }

    /**
     * Finds a room by its building number and room number.
     *
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return ResponseEntity containing the found room
     * @throws ResourceNotFoundException if the room is not found
     */
    @GetMapping("buildings/{buildingNumber}/rooms/{roomNumber}")
    public ResponseEntity<RoomDTO> findByBuildingNumberAndRoomNumber(@PathVariable String buildingNumber, @PathVariable String roomNumber) {
        var roomFound = roomService.findByBuildingNumberAndRoomNumber(buildingNumber, roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return ResponseEntity.ok(fromRoom(roomFound));
    }

    /**
     * Retrieves all rooms in a specific building.
     *
     * @param buildingNumber the building number
     * @return List of rooms in the specified building
     */
    @GetMapping("buildings/{buildingNumber}/rooms")
    public List<RoomDTO> findByBuilding(@PathVariable String buildingNumber) {
        return roomService.findByBuildingNumber(buildingNumber).stream().map(RoomDTO::fromRoom).toList();
    }

    /**
     * Creates a new room in a specific building.
     *
     * @param buildingNumber the building number where the room is created
     * @param room the new room as DTO
     * @return ResponseEntity containing the created room (HTTP Status 201 CREATED)
     */
    @PostMapping("buildings/{buildingNumber}/rooms")
    public ResponseEntity<RoomDTO> createNew(@PathVariable String buildingNumber, @RequestBody RoomDTO room) {
        var newRoom = roomService.createNew(buildingNumber, room.toRoom());
        return ResponseEntity.status(CREATED).body(fromRoom(newRoom));
    }

    /**
     * Updates an existing room.
     *
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number to update
     * @param room the updated room data as DTO
     * @return ResponseEntity containing the updated room
     */
    @PutMapping("buildings/{buildingNumber}/rooms/{roomNumber}")
    public ResponseEntity<RoomDTO> update(@PathVariable String buildingNumber, @PathVariable String roomNumber, @RequestBody RoomDTO room) {
        var updatedRoom = roomService.update(buildingNumber, roomNumber, room.toRoom());
        return ResponseEntity.ok(fromRoom(updatedRoom));
    }

    /**
     * Deletes a room.
     *
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number to delete
     * @return ResponseEntity with HTTP Status 200 OK
     */
    @DeleteMapping("buildings/{buildingNumber}/rooms/{roomNumber}")
    public ResponseEntity<RoomDTO> delete(@PathVariable String buildingNumber, @PathVariable String roomNumber) {
        roomService.delete(buildingNumber, roomNumber);
        return ResponseEntity.ok().build();
    }

}
