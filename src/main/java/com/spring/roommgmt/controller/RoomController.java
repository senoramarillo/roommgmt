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

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("rooms")
    public List<RoomDTO> findAll() {
        return roomService.findAll().stream().map(RoomDTO::fromRoom).toList();
    }

    @GetMapping("rooms/public")
    public List<RoomDTO> findByBuilding() {
        return roomService.findPublic().stream().map(RoomDTO::fromRoom).toList();
    }

    @GetMapping("buildings/{buildingNumber}/rooms/{roomNumber}")
    public ResponseEntity<RoomDTO> findByBuildingNumberAndRoomNumber(@PathVariable String buildingNumber, @PathVariable String roomNumber) {
        var roomFound = roomService.findByBuildingNumberAndRoomNumber(buildingNumber, roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return ResponseEntity.ok(fromRoom(roomFound));
    }

    @GetMapping("buildings/{buildingNumber}/rooms")
    public List<RoomDTO> findByBuilding(@PathVariable String buildingNumber) {
        return roomService.findByBuildingNumber(buildingNumber).stream().map(RoomDTO::fromRoom).toList();
    }

    @PostMapping("buildings/{buildingNumber}/rooms")
    public ResponseEntity<RoomDTO> createNew(@PathVariable String buildingNumber, @RequestBody RoomDTO room) {
        var newRoom = roomService.createNew(buildingNumber, room.toRoom());
        return ResponseEntity.status(CREATED).body(fromRoom(newRoom));
    }

    @PutMapping("buildings/{buildingNumber}/rooms/{roomNumber}")
    public ResponseEntity<RoomDTO> update(@PathVariable String buildingNumber, @PathVariable String roomNumber, @RequestBody RoomDTO room) {
        var updatedRoom = roomService.update(buildingNumber, roomNumber, room.toRoom());
        return ResponseEntity.ok(fromRoom(updatedRoom));
    }

    @DeleteMapping("buildings/{buildingNumber}/rooms/{roomNumber}")
    public ResponseEntity<RoomDTO> delete(@PathVariable String buildingNumber, @PathVariable String roomNumber) {
        roomService.delete(buildingNumber, roomNumber);
        return ResponseEntity.ok().build();
    }

}
