package com.spring.roommgmt.model;

import java.time.Instant;

/**
 * Criteria Record for Meeting Searches.
 * Used to filter meetings by date range, building number, and room number.
 * All criteria fields are optional (can be null).
 *
 * @param start the start time filter (inclusive), or null for no filter
 * @param end the end time filter (inclusive), or null for no filter
 * @param buildingNumber the building number filter, or null for no filter
 * @param roomNumber the room number filter, or null for no filter
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public record MeetingCriteria(Instant start, Instant end, String buildingNumber, String roomNumber) {
}
