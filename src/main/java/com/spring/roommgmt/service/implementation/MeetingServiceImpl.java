package com.spring.roommgmt.service.implementation;

import com.spring.roommgmt.model.Meeting;
import com.spring.roommgmt.model.MeetingCriteria;
import com.spring.roommgmt.model.Room;
import com.spring.roommgmt.repository.MeetingRepository;
import com.spring.roommgmt.repository.MeetingSpecifications;
import com.spring.roommgmt.service.MeetingService;
import com.spring.roommgmt.service.RoomService;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    private final RoomService roomService;

    @Override
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    @Override
    public Optional<Meeting> findById(Long id) {
        return meetingRepository.findById(id);
    }

    @Override
    public List<Meeting> findByCriteria(MeetingCriteria criteria) {
        var specification = MeetingSpecifications.meetsCriteria(criteria);
        return specification.map(meetingRepository::findAll).orElseGet(this::findAll);
    }

    @Override
    public Meeting createNew(String buildingNumber, String roomNumber, Meeting meeting) {
        var room = findRoomOrThrow(buildingNumber, roomNumber);
        meeting.setRoom(room);
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting update(Long meetingId, String buildingNumber, String roomNumber, Meeting meeting) {
        if (!meetingId.equals(meeting.getId())) {
            throw new IllegalArgumentException();
        }
        var room = findRoomOrThrow(buildingNumber, roomNumber);
        var meetingToBeUpdated = findById(meetingId).orElseThrow(ResourceNotFoundException::new);
        return update(meetingToBeUpdated, meeting, room);
    }

    private Meeting update(Meeting meetingToBeUpdated, Meeting meeting, Room room) {
        meetingToBeUpdated.setTopic(meeting.getTopic());
        meetingToBeUpdated.setStart(meeting.getStart());
        meetingToBeUpdated.setEnd(meeting.getEnd());
        meetingToBeUpdated.setRoom(room);
        return meetingToBeUpdated;
    }

    @Override
    public void delete(Long meetingId) {
        var meetingToBeDeleted = findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));
        meetingRepository.delete(meetingToBeDeleted);
    }

    private Room findRoomOrThrow(String buildingNumber, String roomNumber) {
        return roomService.findByBuildingNumberAndRoomNumber(buildingNumber, roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

}
