package com.example.backend.meetings_schedule;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.meetings_schedule.dto.MeetingsDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.timeChunks.TimeChunks;
import com.example.backend.timeChunks.TimeChunksService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.backend.client.timekeeper.Activity.CONSULTANCY_TIME;
import static com.example.backend.client.timekeeper.Activity.REMAINING_DAYS;
import static com.example.backend.meetings_schedule.Meetings.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class MeetingsScheduleService {

    private final ConsultantService consultantService;
    private final TimeChunksService timeChunksService;
    private final RegisteredTimeService registeredTimeService;
    private final MeetingsScheduleRepository meetingsScheduleRepository;

    public MeetingsScheduleService(
            @Lazy
            ConsultantService consultantService,
            TimeChunksService timeChunksService,
            RegisteredTimeService registeredTimeService,
            MeetingsScheduleRepository meetingsScheduleRepository) {
        this.consultantService = consultantService;
        this.timeChunksService = timeChunksService;
        this.registeredTimeService = registeredTimeService;
        this.meetingsScheduleRepository = meetingsScheduleRepository;
    }

    public void assignMeetingsDatesForActiveConsultants() {
        List<Consultant> activeConsultants = consultantService.getAllActiveConsultants();
        for (Consultant consultant : activeConsultants) {
            List<String> clients = registeredTimeService.getClientsByConsultantId(consultant.getId());
            List<TimeChunks> timeChunksByConsultant;
            switch (clients.size()) {
                case 0 -> {
                    continue;
                }
                case 1 -> timeChunksByConsultant = timeChunksService.getTimeChunksByConsultant(consultant.getId());
                default -> {
                    String currentClient = clients.getLast();
                    timeChunksByConsultant = timeChunksService.getTimeChunksByConsultantIdAndClient(
                            consultant.getId(), List.of(currentClient, REMAINING_DAYS.activity));
                }
            }
            createMeetings(consultant, timeChunksByConsultant);

        }
    }

    private void createMeetings(Consultant consultant, List<TimeChunks> timeChunks) {
        int firstMeetingWeeks = 4;
        int thirdMeetingWeeks = 8;
        // Logic for forth meeting commented out as it is not used in the current implementation
//        int fourthMeetingWeeks = 2;
        LocalDate firstMeeting = getFirstMeetingDate(timeChunks, firstMeetingWeeks);
        LocalDate secondMeeting = getSecondMeetingDate(timeChunks);
        LocalDate thirdMeeting = getThirdOrFourthMeetingDate(timeChunks, thirdMeetingWeeks);
//        LocalDate fourthMeeting = getThirdOrFourthMeetingDate(timeChunks, fourthMeetingWeeks);
        if (firstMeeting != null) {
            saveMeeting(consultant, FIRST, firstMeeting);
            saveMeeting(consultant, SECOND, secondMeeting);
            saveMeeting(consultant, THIRD, thirdMeeting);
//            saveMeeting(consultant, FOURTH, fourthMeeting);
        }
    }

    public void saveMeeting(Consultant consultant, Meetings meetingTitle, LocalDate meetingDate) {
        meetingsScheduleRepository.save(new MeetingsSchedule(
                new MeetingsScheduleKey(consultant.getId(),
                        meetingTitle),
                meetingDate,
                "Upcoming",
                consultant.getSaltUser()));
    }

    public LocalDate getFirstMeetingDate(List<TimeChunks> timeChunks, int weeks) {
        int nonConsultancyTime = 0;
        LocalDateTime firstDayOfWork = getFirstDayOfWork(timeChunks);
        if (firstDayOfWork == null) {
            return null;
        }
        for (TimeChunks tc : timeChunks) {
            if (!tc.getType().equalsIgnoreCase(CONSULTANCY_TIME.activity)
                    && !tc.getType().equalsIgnoreCase(REMAINING_DAYS.activity)
                    && tc.getId().getStartDate().isAfter(firstDayOfWork)
                    && tc.getId().getStartDate().isBefore(firstDayOfWork.plusWeeks(weeks))) {
                nonConsultancyTime += tc.getTotalDays();
            }
        }
        return firstDayOfWork.plusWeeks(weeks).plusDays(nonConsultancyTime).toLocalDate();
    }

    public LocalDate getSecondMeetingDate(List<TimeChunks> timeChunks) {
        LocalDateTime firstDayOfWork = getFirstDayOfWork(timeChunks);
        if (firstDayOfWork == null) {
            return null;
        }
        LocalDateTime estimatedEndDate = timeChunks.getLast().getEndDate();
        long countWeeksBetween = DAYS.between(firstDayOfWork, estimatedEndDate);
        return firstDayOfWork.plusDays(countWeeksBetween / 2).toLocalDate();
    }

    public LocalDate getThirdOrFourthMeetingDate(List<TimeChunks> timeChunks, int weeks) {
        LocalDateTime estimatedEndDate = timeChunks.getLast().getEndDate();
        return estimatedEndDate.toLocalDate().minusWeeks(weeks);
    }


    private LocalDateTime getFirstDayOfWork(List<TimeChunks> timeChunks) {
        TimeChunks consultancyStart = timeChunks.stream()
                .filter(t -> t.getType().equalsIgnoreCase(CONSULTANCY_TIME.activity)).findFirst().orElse(null);
        return consultancyStart == null ? null : consultancyStart.getId().getStartDate();
    }

    public List<MeetingsDto> getMeetingsDto(UUID id) {
        List<MeetingsSchedule> meetingsSchedules = meetingsScheduleRepository.findAllById_ConsultantIdOrderByDateAsc(id);
        if (meetingsSchedules.isEmpty()) {
            return new ArrayList<>();
        }
        return meetingsSchedules.stream()
                .map(MeetingsDto::toDto).toList();
    }
}
