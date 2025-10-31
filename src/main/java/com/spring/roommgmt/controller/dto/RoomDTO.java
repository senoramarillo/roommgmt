package com.spring.roommgmt.controller.dto;

import com.spring.roommgmt.model.Room;

public record RoomDTO(Long id, Long buildingId, String buildingNumber, String roomNumber, Integer seats, boolean projectorPresent) {

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

    public Room toRoom() {
        return new Room(id, roomNumber, seats, projectorPresent);
    }

}
