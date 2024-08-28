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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.backend.client.timekeeper.Activity.REMAINING_DAYS;
import static com.example.backend.meetings_schedule.Meetings.*;

@Service
public class MeetingsScheduleService {

    private ConsultantService consultantService;
    private TimeChunksService timeChunksService;
    private RegisteredTimeService registeredTimeService;
    private MeetingsScheduleRepository meetingsScheduleRepository;

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

    public void createMeetingsForActiveConsultantsNonPgp() {
        List<Consultant> activeConsultants = consultantService.getAllActiveConsultants();
        System.out.println("activeConsultants.size() = " + activeConsultants.size());
        for (Consultant consultant : activeConsultants) {
            System.out.println("-----------------------");
            System.out.println("consultant.getFullName() = " + consultant.getFullName());
            System.out.println("consultant.getId() = " + consultant.getId());
        }
        int firstMeetingWeeks = 44;
        int secondMeetingWeeks = 20;
        int thirdMeetingWeeks = 8;
        int fourthMeetingWeeks = 2;
        for (Consultant consultant : activeConsultants) {
            List<String> clients = registeredTimeService.getClientsByConsultantId(consultant.getId());
            List<TimeChunks> timeChunksByConsultant = new ArrayList<>();
            if (clients.size() == 1) {
                timeChunksByConsultant = timeChunksService.getTimeChunksByConsultant(consultant.getId());
            } else if (clients.size() > 1) {
                String currentClient = clients.getLast();
                timeChunksByConsultant = timeChunksService.getTimeChunksByConsultantIdAndClient(
                        consultant.getId(), List.of(currentClient, REMAINING_DAYS.activity));
            } else continue;


            LocalDate firstMeeting = getMeetingDate(timeChunksByConsultant, firstMeetingWeeks);
            if (firstMeeting == null) {
                continue;
            }
//            LocalDate secondMeeting = getMeetingDate(timeChunksByConsultant, secondMeetingWeeks);
            LocalDate thirdMeeting = getMeetingDate(timeChunksByConsultant, thirdMeetingWeeks);
            LocalDate fourthMeeting = getMeetingDate(timeChunksByConsultant, fourthMeetingWeeks);
            UUID consultantId = consultant.getId();
            System.out.println("consultant = " + consultant.getFullName());
            System.out.println("firstMeeting = " + firstMeeting);
            System.out.println("thirdMeeting = " + thirdMeeting);
            System.out.println("fourthMeeting = " + fourthMeeting);
            meetingsScheduleRepository.save(new MeetingsSchedule(
                    new MeetingsScheduleKey(consultantId,
                    FIRST),
                    firstMeeting,
                    "Upcomming",
                    consultant.getSaltUser()));
            meetingsScheduleRepository.save(new MeetingsSchedule(
                    new MeetingsScheduleKey(consultantId,
                    THIRD),
                    thirdMeeting,
                    "Upcomming",
                    consultant.getSaltUser()));
            meetingsScheduleRepository.save(new MeetingsSchedule(
                    new MeetingsScheduleKey(consultantId,
                    FOURTH),
                    fourthMeeting,
                    "Upcomming",
                    consultant.getSaltUser()));
        }
    }

    public LocalDate getMeetingDate(List<TimeChunks> timeChunks, int weeks) {
//        LocalDate firstDayAtWork = timeChunks.stream()
//                .filter(tc -> tc.getType().equalsIgnoreCase(CONSULTANCY_TIME.activity))
//                .toList().getFirst().getId().getStartDate().toLocalDate();
        TimeChunks remainingDays = timeChunks.stream()
                .filter(t -> t.getType().equalsIgnoreCase(REMAINING_DAYS.activity)).findFirst().orElse(null);
        if (remainingDays == null) {
            return null;
        }
        return remainingDays.getEndDate().toLocalDate().minusWeeks(weeks);

//        int weekFirstDay = firstDayAtWork.get(WeekFields.of(Locale.ENGLISH).weekOfYear());
//        int firstKonsultTidDays = timeChunks.getFirst().getTotalDays();
//        if (firstKonsultTidDays >= workingDaysTillFirstMeeting) {
//            return nextMeetingDate;
//        } else {
////            List<TimeChunks> nonConsultancyTime = timeChunks.stream()
//            int nonConsultancyTime = 0;
//            for (TimeChunks tc : timeChunks) {
//                if (!tc.getType().equalsIgnoreCase(CONSULTANCY_TIME.activity)
//                        && tc.getId().getStartDate().getDayOfYear() > startDate.getDayOfYear()
//                        && tc.getId().getStartDate().getDayOfYear() < nextMeetingDate.getDayOfYear()) {
//                    nonConsultancyTime += tc.getTotalDays();
//                }
//            }
//            System.out.println("nonConsultancyTime = " + nonConsultancyTime);
//            System.out.println("nextMeetingDate before = " + nextMeetingDate);
//            nextMeetingDate = nextMeetingDate.plusDays(nonConsultancyTime);
//        }
//        return nextMeetingDate;
    }

    public List<MeetingsDto> getMeetingsDto(UUID id) {
        List<MeetingsSchedule> meetingsSchedules = meetingsScheduleRepository.findAllById_ConsultantId(id);
        System.out.println("meetingsSchedules = " + meetingsSchedules);
        return meetingsSchedules.stream()
                .map(MeetingsDto::toDto).toList();
    }
}
