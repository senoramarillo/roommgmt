package com.spring.roommgmt.service;

import com.spring.roommgmt.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> findAll();

    Optional<Room> findByBuildingNumberAndRoomNumber(String buildingNumber, String roomNumber);

    List<Room> findByBuildingNumber(String buildingNumber);

    List<Room> findPublic();

    Room createNew(String buildingNumber, Room room);

    Room update(String buildingNumber, String roomNumber, Room room);

    void delete(String buildingNumber, String roomNumber);
}
