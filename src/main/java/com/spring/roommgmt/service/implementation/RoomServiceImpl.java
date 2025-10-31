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

@Service
@Transactional
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;

    private final BuildingService buildingService;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findByBuildingNumberAndRoomNumber(String buildingNumber, String roomNumber) {
        var building = findBuildingOrThrow(buildingNumber);
        return roomRepository.findByBuildingAndRoomNumber(building, roomNumber);
    }

    @Override
    public List<Room> findByBuildingNumber(String buildingNumber) {
        var building = findBuildingOrThrow(buildingNumber);
        return roomRepository.findByBuilding(building);
    }

    @Override
    public List<Room> findPublic() {
        return roomRepository.findPublicRooms();
    }

    @Override
    public Room createNew(String buildingNumber, Room room) {
        var building = findBuildingOrThrow(buildingNumber);
        if (findByBuildingNumberAndRoomNumber(building.getBuildingNumber(), room.getRoomNumber()).isPresent()) {
            throw new DuplicateKeyException();
        }
        room.setBuilding(building);
        return roomRepository.save(room);
    }

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

    @Override
    public void delete(String buildingNumber, String roomNumber) {
        var roomToBeDeleted = findRoomOrThrow(buildingNumber, roomNumber);
        roomRepository.delete(roomToBeDeleted);
    }

    private Building findBuildingOrThrow(String buildingNumber) {
        return buildingService.findByBuildingNumber(buildingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
    }

    private Room findRoomOrThrow(String buildingNumber, String roomNumber) {
        return findByBuildingNumberAndRoomNumber(buildingNumber, roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

}