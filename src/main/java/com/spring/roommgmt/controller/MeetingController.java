package com.spring.roommgmt.controller;

import com.spring.roommgmt.controller.dto.MeetingDTO;
import com.spring.roommgmt.model.MeetingCriteria;
import com.spring.roommgmt.service.MeetingService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.spring.roommgmt.controller.dto.MeetingDTO.fromMeeting;
import static com.spring.roommgmt.model.Objects.anyNonNull;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public List<MeetingDTO> find(@RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime start,
                                 @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyyMMddHHmm") LocalDateTime end,
                                 @RequestParam(name = "building-number", required = false) String buildingNumber,
                                 @RequestParam(name = "room-number", required = false) String roomNumber) {
        if (hasAnyRestrictionBy(start, end, buildingNumber, roomNumber)) {
            var criteria = createCriteriaFrom(start, end, buildingNumber, roomNumber);
            return findByCriteria(criteria);
        }
        return findAll();
    }

    private boolean hasAnyRestrictionBy(LocalDateTime start, LocalDateTime end, String buildingNumber, String roomNumber) {
        return anyNonNull(start, end, buildingNumber, roomNumber);
    }

    private MeetingCriteria createCriteriaFrom(LocalDateTime start, LocalDateTime end, String buildingNumber, String roomNumber) {
        return new MeetingCriteria(
                start != null ? start.atZone(ZoneId.systemDefault()).toInstant() : null,
                end != null ? end.atZone(ZoneId.systemDefault()).toInstant() : null,
                buildingNumber,
                roomNumber
        );
    }

    private List<MeetingDTO> findAll() {
        return meetingService.findAll().stream().map(MeetingDTO::fromMeeting).toList();
    }

    private List<MeetingDTO> findByCriteria(MeetingCriteria criteria) {
        return meetingService.findByCriteria(criteria).stream().map(MeetingDTO::fromMeeting).toList();
    }

    @PostMapping
    public ResponseEntity<MeetingDTO> createNew(@RequestBody MeetingDTO meeting) {
        var newMeeting = meetingService.createNew(meeting.buildingNumber(), meeting.roomNumber(), meeting.toMeeting());
        return ResponseEntity.status(CREATED).body(fromMeeting(newMeeting));
    }

    @PutMapping("{meetingId}")
    public ResponseEntity<MeetingDTO> update(@PathVariable Long meetingId, @RequestBody MeetingDTO dto) {
        var updatedMeeting = meetingService.update(meetingId, dto.buildingNumber(), dto.roomNumber(), dto.toMeeting());
        return ResponseEntity.ok(fromMeeting(updatedMeeting));
    }

    @DeleteMapping("{meetingId}")
    public ResponseEntity<MeetingDTO> delete(@PathVariable Long meetingId) {
        meetingService.delete(meetingId);
        return ResponseEntity.ok().build();
    }

}
