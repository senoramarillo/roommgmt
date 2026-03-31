package com.spring.roommgmt.controller.dto;

import com.spring.roommgmt.model.Meeting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * Data Transfer Object for Meeting.
 * Used for transferring meeting data between client and server.
 *
 * @param id the unique identifier of the meeting
 * @param roomId the unique identifier of the room where the meeting is held
 * @param buildingNumber the building number containing the room
 * @param roomNumber the room number where the meeting is held
 * @param topic the topic or title of the meeting
 * @param start the start time of the meeting
 * @param end the end time of the meeting
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public record MeetingDTO(Long id, Long roomId,
                         @NotBlank String buildingNumber, @NotBlank String roomNumber,
                         @NotBlank String topic, @NotNull Instant start, @NotNull Instant end) {

    /**
     * Converts a Meeting entity to a MeetingDTO.
     *
     * @param meeting the Meeting entity to convert
     * @return the converted MeetingDTO
     */
    public static MeetingDTO fromMeeting(Meeting meeting) {
        return new MeetingDTO(
                meeting.getId(),
                meeting.getRoom().getId(),
                meeting.getRoom().getBuilding().getBuildingNumber(),
                meeting.getRoom().getRoomNumber(),
                meeting.getTopic(),
                meeting.getStart(),
                meeting.getEnd()
        );
    }

    /**
     * Converts this MeetingDTO to a Meeting entity.
     *
     * @return the converted Meeting entity
     */
    public Meeting toMeeting() {
        return new Meeting(id, topic, start, end);
    }

}
