package com.spring.roommgmt.service;

import com.spring.roommgmt.model.Meeting;
import com.spring.roommgmt.model.MeetingCriteria;

import java.util.List;
import java.util.Optional;

public interface MeetingService {

    List<Meeting> findAll();

    Optional<Meeting> findById(Long id);

    List<Meeting> findByCriteria(MeetingCriteria criteria);

    Meeting createNew(String buildingNumber, String roomNumber, Meeting meeting);

    Meeting update(Long meetingId, String buildingNumber, String roomNumber, Meeting meeting);

    void delete(Long meetingId);

}
