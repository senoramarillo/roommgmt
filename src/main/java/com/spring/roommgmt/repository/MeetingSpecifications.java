package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Specifications class for building dynamic Criteria API queries for Meeting entities.
 * Provides static methods to create Specification objects for filtering meetings
 * by start time, end time, building, and room.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingSpecifications {

    /**
     * Creates a combined Specification from the provided MeetingCriteria.
     * Builds a list of specifications based on non-null criteria fields
     * and combines them using the AND operator.
     *
     * @param criteria the meeting criteria containing filters
     * @return Optional containing the combined specification, or empty if no criteria are provided
     */
    public static Optional<Specification<Meeting>> meetsCriteria(MeetingCriteria criteria) {
        var specifications = new ArrayList<Specification<Meeting>>();
        if (criteria.start() != null) {
            specifications.add(startsAfter(criteria.start()));
        }
        if (criteria.end() != null) {
            specifications.add(endsBefore(criteria.end()));
        }
        if (criteria.buildingNumber() != null) {
            if (criteria.roomNumber() != null) {
                specifications.add(isInRoom(criteria.buildingNumber(), criteria.roomNumber()));
            } else {
                specifications.add(isInBuilding(criteria.buildingNumber()));
            }
        }
        return specifications.stream().reduce(Specification::and);
    }

    /**
     * Creates a Specification for meetings starting on or after the specified instant.
     *
     * @param start the start time (inclusive)
     * @return Specification for meetings starting after the given time
     */
    public static Specification<Meeting> startsAfter(Instant start) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Meeting_.start), start);
    }

    /**
     * Creates a Specification for meetings ending on or before the specified instant.
     *
     * @param end the end time (inclusive)
     * @return Specification for meetings ending before the given time
     */
    public static Specification<Meeting> endsBefore(Instant end) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Meeting_.end), end);
    }

    /**
     * Creates a Specification for meetings in a specific building.
     *
     * @param buildingNumber the building number to filter by
     * @return Specification for meetings in the specified building
     */
    public static Specification<Meeting> isInBuilding(String buildingNumber) {
        return (root, query, builder) -> {
            var roomJoin = root.join(String.valueOf(Meeting_.room));
            var buildingJoin = roomJoin.join(String.valueOf(Room_.building));
            return builder.equal(buildingJoin.get(String.valueOf(Building_.buildingNumber)), buildingNumber);
        };
    }

    /**
     * Creates a Specification for meetings in a specific room within a building.
     *
     * @param buildingNumber the building number to filter by
     * @param roomNumber the room number to filter by
     * @return Specification for meetings in the specified room
     */
    public static Specification<Meeting> isInRoom(String buildingNumber, String roomNumber) {
        return (root, query, builder) -> {
            var roomJoin = root.join(String.valueOf(Meeting_.room));
            var buildingJoin = roomJoin.join(String.valueOf(Room_.building));
            return builder.and(
                    builder.equal(roomJoin.get(String.valueOf(Room_.roomNumber)), roomNumber),
                    builder.equal(buildingJoin.get(String.valueOf(Building_.buildingNumber)), buildingNumber)
            );
        };
    }

}
