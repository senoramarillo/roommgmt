package com.spring.roommgmt.model;

import java.time.Instant;

public record MeetingCriteria(Instant start, Instant end, String buildingNumber, String roomNumber) {
}
