package com.spring.roommgmt.controller.dto;

import com.spring.roommgmt.model.Meeting;

import java.time.Instant;

public record MeetingDTO(Long id, Long roomId,
                         String buildingNumber, String roomNumber,
                         String topic, Instant start, Instant end) {

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

    public Meeting toMeeting() {
        return new Meeting(id, topic, start, end);
    }

}
