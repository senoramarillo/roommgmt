package com.spring.roommgmt.controller;

import com.spring.roommgmt.controller.dto.MeetingDTO;
import com.spring.roommgmt.model.MeetingCriteria;
import com.spring.roommgmt.service.MeetingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.spring.roommgmt.controller.dto.MeetingDTO.fromMeeting;
import static com.spring.roommgmt.model.Objects.anyNonNull;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST Controller for Meeting Management.
 * Provides endpoints for retrieving, creating, updating, and deleting meetings.
 * Supports filtering meetings by date range, building number, and room number.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("meetings")
public class MeetingController {

    private final MeetingService meetingService;

    /**
     * Finds meetings with optional filters.
     * If no filters are provided, returns all meetings.
     *
     * @param start the start date-time filter (format: yyyyMMddHHmm)
     * @param end the end date-time filter (format: yyyyMMddHHmm)
     * @param buildingNumber the building number filter
     * @param roomNumber the room number filter
     * @return List of meetings matching the criteria
     */
    @GetMapping
    public List<MeetingDTO> find(@RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime start,
                                 @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime end,
                                 @RequestParam(name = "building-number", required = false) String buildingNumber,
                                 @RequestParam(name = "room-number", required = false) String roomNumber) {
        if (hasAnyRestrictionBy(start, end, buildingNumber, roomNumber)) {
            var criteria = createCriteriaFrom(start, end, buildingNumber, roomNumber);
            return findByCriteria(criteria);
        }
        return findAll();
    }

    /**
     * Creates a new meeting.
     *
     * @param meeting the new meeting as DTO
     * @return ResponseEntity containing the created meeting (HTTP Status 201 CREATED)
     */
    @PostMapping
    public ResponseEntity<MeetingDTO> createNew(@Valid @RequestBody MeetingDTO meeting) {
        var newMeeting = meetingService.createNew(meeting.buildingNumber(), meeting.roomNumber(), meeting.toMeeting());
        return ResponseEntity.status(CREATED).body(fromMeeting(newMeeting));
    }

    /**
     * Updates an existing meeting.
     *
     * @param meetingId the ID of the meeting to update
     * @param dto the updated meeting data as DTO
     * @return ResponseEntity containing the updated meeting
     */
    @PutMapping("{meetingId}")
    public ResponseEntity<MeetingDTO> update(@PathVariable Long meetingId, @Valid @RequestBody MeetingDTO dto) {
        var updatedMeeting = meetingService.update(meetingId, dto.buildingNumber(), dto.roomNumber(), dto.toMeeting());
        return ResponseEntity.ok(fromMeeting(updatedMeeting));
    }

    /**
     * Deletes a meeting.
     *
     * @param meetingId the ID of the meeting to delete
     * @return ResponseEntity with HTTP Status 200 OK
     */
    @DeleteMapping("{meetingId}")
    public ResponseEntity<MeetingDTO> delete(@PathVariable Long meetingId) {
        meetingService.delete(meetingId);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks if any restriction filters are provided.
     *
     * @param start the start date-time
     * @param end the end date-time
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return true if any filter is provided
     */
    private boolean hasAnyRestrictionBy(LocalDateTime start, LocalDateTime end, String buildingNumber, String roomNumber) {
        return anyNonNull(start, end, buildingNumber, roomNumber);
    }

    /**
     * Creates a MeetingCriteria from filter parameters.
     *
     * @param start the start date-time
     * @param end the end date-time
     * @param buildingNumber the building number
     * @param roomNumber the room number
     * @return MeetingCriteria with the provided filters
     */
    private MeetingCriteria createCriteriaFrom(LocalDateTime start, LocalDateTime end, String buildingNumber, String roomNumber) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("Meeting search start must be before or equal to end");
        }
        if (roomNumber != null && buildingNumber == null) {
            throw new IllegalArgumentException("room-number filter requires building-number");
        }
        return new MeetingCriteria(
                start != null ? start.atZone(ZoneId.systemDefault()).toInstant() : null,
                end != null ? end.atZone(ZoneId.systemDefault()).toInstant() : null,
                buildingNumber,
                roomNumber
        );
    }

    /**
     * Retrieves all meetings.
     *
     * @return List of all meetings
     */
    private List<MeetingDTO> findAll() {
        return meetingService.findAll().stream().map(MeetingDTO::fromMeeting).toList();
    }

    /**
     * Retrieves meetings matching the specified criteria.
     *
     * @param criteria the search criteria
     * @return List of meetings matching the criteria
     */
    private List<MeetingDTO> findByCriteria(MeetingCriteria criteria) {
        return meetingService.findByCriteria(criteria).stream().map(MeetingDTO::fromMeeting).toList();
    }

}
