package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Building;
import com.spring.roommgmt.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByBuildingAndRoomNumber(Building building, String roomNumber);

    List<Room> findByBuilding(Building building);

    @Query("select room from Room room join room.building building where building.publicAccess = true")
    List<Room> findPublicRooms();

}