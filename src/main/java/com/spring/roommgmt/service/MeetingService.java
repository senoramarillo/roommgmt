package com.spring.roommgmt.service;

import com.spring.roommgmt.model.Meeting;
import com.spring.roommgmt.model.MeetingCriteria;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for Meeting Management.
 * Defines business logic for managing meetings scheduled in rooms.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface MeetingService {

    /**
     * Retrieves all meetings.
     *
     * @return List of all meetings
     */
    List<Meeting> findAll();

    /**
     * Finds a meeting by its ID.
     *
     * @param id the meeting ID
     * @return Optional containing the found meeting
     */
    Optional<Meeting> findById(Long id);

    /**
     * Finds meetings matching the specified criteria.
     *
     * @param criteria the search criteria
     * @return List of meetings matching the criteria
     */
    List<Meeting> findByCriteria(MeetingCriteria criteria);

    /**
     * Creates a new meeting in a specific room.
     *
     * @param buildingNumber the building number where the meeting is scheduled
     * @param roomNumber the room number where the meeting is scheduled
     * @param meeting the new meeting
     * @return the created meeting with generated ID
     */
    Meeting createNew(String buildingNumber, String roomNumber, Meeting meeting);

    /**
     * Updates an existing meeting.
     *
     * @param meetingId the ID of the meeting to update
     * @param buildingNumber the building number containing the room
     * @param roomNumber the room number where the meeting will be held
     * @param meeting the updated meeting data
     * @return the updated meeting
     */
    Meeting update(Long meetingId, String buildingNumber, String roomNumber, Meeting meeting);

    /**
     * Deletes a meeting.
     *
     * @param meetingId the ID of the meeting to delete
     */
    void delete(Long meetingId);

}
