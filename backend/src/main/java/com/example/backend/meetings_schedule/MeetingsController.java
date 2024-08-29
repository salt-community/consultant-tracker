package com.example.backend.meetings_schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@CrossOrigin
public class MeetingsController {
    private final MeetingsScheduleService meetingsScheduleService;

//    public MeetingsController(MeetingsScheduleService meetingsScheduleService) {
//        this.meetingsScheduleService = meetingsScheduleService;
//    }

    @GetMapping
    public void saveMeetings() {
        meetingsScheduleService.assignMeetingsDatesForActiveConsultants();
    }
}
