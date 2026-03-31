package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Meeting;
import com.spring.roommgmt.model.Room;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;

/**
 * Repository interface for Meeting entities.
 * Provides database access operations for meetings using Spring Data JPA.
 * Extends JpaSpecificationExecutor to support dynamic query criteria.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    @Override
    @EntityGraph(attributePaths = {"room", "room.building"})
    java.util.List<Meeting> findAll();

    @Override
    @EntityGraph(attributePaths = {"room", "room.building"})
    java.util.List<Meeting> findAll(Specification<Meeting> specification);

    @Override
    @EntityGraph(attributePaths = {"room", "room.building"})
    java.util.Optional<Meeting> findById(Long id);

    boolean existsByRoom(Room room);

    boolean existsByRoomAndStartLessThanAndEndGreaterThan(Room room, Instant end, Instant start);

    boolean existsByRoomAndStartLessThanAndEndGreaterThanAndIdNot(Room room, Instant end, Instant start, Long id);
}
