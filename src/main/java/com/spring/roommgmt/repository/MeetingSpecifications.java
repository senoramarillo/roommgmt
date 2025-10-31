package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingSpecifications {

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

    public static Specification<Meeting> startsAfter(Instant start) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Meeting_.start), start);
    }

    public static Specification<Meeting> endsBefore(Instant end) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(String.valueOf(Meeting_.end)), end);
    }

    public static Specification<Meeting> isInBuilding(String buildingNumber) {
        return (root, query, builder) -> {
            var roomJoin = root.join(String.valueOf(Meeting_.room));
            var buildingJoin = roomJoin.join(String.valueOf(Room_.building));
            return builder.like(buildingJoin.get(String.valueOf(Building_.buildingNumber)), buildingNumber);
        };
    }

    public static Specification<Meeting> isInRoom(String buildingNumber, String roomNumber) {
        return (root, query, builder) -> {
            var roomJoin = root.join(String.valueOf(Meeting_.room));
            var buildingJoin = roomJoin.join(String.valueOf(Room_.building));
            return builder.and(
                    builder.like(roomJoin.get(String.valueOf(Room_.roomNumber)), roomNumber),
                    builder.like(buildingJoin.get(String.valueOf(Building_.buildingNumber)), buildingNumber)
            );
        };
    }

}
