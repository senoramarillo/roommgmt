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

/**
 * Service implementation for Meeting Management.
 * Provides concrete implementations of meeting business logic.
 * Handles creation, updating, deletion, and retrieval of meetings.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    private final RoomService roomService;

    /**
     * Retrieves all meetings from the repository.
     *
     * @return List of all meetings
     */
    @Override
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    /**
     * Finds a meeting by its ID.
     *
     * @param id the meeting ID
     * @return Optional containing the found meeting
     */
    @Override
    public Optional<Meeting> findById(Long id) {
        return meetingRepository.findById(id);
    }

    /**
     * Finds meetings matching the specified criteria.
     * Criteria can include date range, building number, and room number filters.
     *
     * @param criteria the search criteria
     * @return List of meetings matching the criteria
     */
    @Override
    public List<Meeting> findByCriteria(MeetingCriteria criteria) {
        var specification = MeetingSpecifications.meetsCriteria(criteria);
        return specification.map(meetingRepository::findAll).orElseGet(this::findAll);
    }

    /**
     * Creates a new meeting in a specific room.
     *
     * @param buildingNumber the building number where the meeting is scheduled
     * @param roomNumber the room number where the meeting is scheduled
     * @param meeting the new meeting to create
     * @return the created meeting with generated ID
     * @throws ResourceNotFoundException if the specified room is not found
     */
    @Override
    public Meeting createNew(String buildingNumber, String roomNumber, Meeting meeting) {
        var room = findRoomOrThrow(buildingNumber, roomNumber);
        meeting.setRoom(room);
        return meetingRepository.save(meeting);
    }

    /**
     * Updates an existing meeting.
     *
     * @param meetingId the ID of the meeting to update
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number where the meeting will be held
     * @param meeting the updated meeting data
     * @return the updated meeting
     * @throws IllegalArgumentException if meeting IDs do not match
     * @throws ResourceNotFoundException if the meeting or room is not found
     */
    @Override
    public Meeting update(Long meetingId, String buildingNumber, String roomNumber, Meeting meeting) {
        if (!meetingId.equals(meeting.getId())) {
            throw new IllegalArgumentException();
        }
        var room = findRoomOrThrow(buildingNumber, roomNumber);
        var meetingToBeUpdated = findById(meetingId).orElseThrow(ResourceNotFoundException::new);
        return update(meetingToBeUpdated, meeting, room);
    }

    /**
     * Deletes a meeting by its ID.
     *
     * @param meetingId the ID of the meeting to delete
     * @throws ResourceNotFoundException if the meeting is not found
     */
    @Override
    public void delete(Long meetingId) {
        var meetingToBeDeleted = findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));
        meetingRepository.delete(meetingToBeDeleted);
    }

    /**
     * Helper method to update meeting properties.
     *
     * @param meetingToBeUpdated the meeting entity to update
     * @param meeting the new meeting data
     * @param room the room where the meeting will be held
     * @return the updated meeting
     */
    private Meeting update(Meeting meetingToBeUpdated, Meeting meeting, Room room) {
        meetingToBeUpdated.setTopic(meeting.getTopic());
        meetingToBeUpdated.setStart(meeting.getStart());
        meetingToBeUpdated.setEnd(meeting.getEnd());
        meetingToBeUpdated.setRoom(room);
        return meetingToBeUpdated;
    }

    /**
     * Helper method to find a room by building number and room number or throw an exception.
     *
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return the found room
     * @throws ResourceNotFoundException if the room is not found
     */
    private Room findRoomOrThrow(String buildingNumber, String roomNumber) {
        return roomService.findByBuildingNumberAndRoomNumber(buildingNumber, roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

}
