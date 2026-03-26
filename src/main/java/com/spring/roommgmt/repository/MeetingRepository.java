package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for Meeting entities.
 * Provides database access operations for meetings using Spring Data JPA.
 * Extends JpaSpecificationExecutor to support dynamic query criteria.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {
}
