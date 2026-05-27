package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Meeting;
import com.spring.roommgmt.model.Room;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
    @NonNull
    @EntityGraph(attributePaths = {"room", "room.building"})
    List<Meeting> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"room", "room.building"})
    List<Meeting> findAll(Specification<Meeting> specification);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"room", "room.building"})
    Optional<Meeting> findById(@NonNull Long id);

    boolean existsByRoom(Room room);

    boolean existsByRoomAndStartLessThanAndEndGreaterThan(Room room, Instant end, Instant start);

    boolean existsByRoomAndStartLessThanAndEndGreaterThanAndIdNot(Room room, Instant end, Instant start, Long id);
}
