package com.spring.roommgmt.controller.dto;

import com.spring.roommgmt.model.Room;

/**
 * Data Transfer Object for Room.
 * Used for transferring room data between client and server.
 *
 * @param id the unique identifier of the room
 * @param buildingId the unique identifier of the building containing the room
 * @param buildingNumber the building number
 * @param roomNumber the unique room number
 * @param seats the number of seats in the room
 * @param projectorPresent indicates if the room has a projector
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public record RoomDTO(Long id, Long buildingId, String buildingNumber, String roomNumber, Integer seats, boolean projectorPresent) {

    /**
     * Converts a Room entity to a RoomDTO.
     *
     * @param room the Room entity to convert
     * @return the converted RoomDTO
     */
    public static RoomDTO fromRoom(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getBuilding().getId(),
                room.getBuilding().getBuildingNumber(),
                room.getRoomNumber(),
                room.getSeats(),
                room.isProjectorPresent()
        );
    }

    /**
     * Converts this RoomDTO to a Room entity.
     *
     * @return the converted Room entity
     */
    public Room toRoom() {
        return new Room(id, roomNumber, seats, projectorPresent);
    }

}
