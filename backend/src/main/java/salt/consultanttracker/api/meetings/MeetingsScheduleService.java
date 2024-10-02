package salt.consultanttracker.api.meetings;

import salt.consultanttracker.api.consultant.Consultant;
import salt.consultanttracker.api.consultant.ConsultantService;
import salt.consultanttracker.api.meetings.dto.MeetingsDto;
import salt.consultanttracker.api.registeredtime.RegisteredTimeService;
import salt.consultanttracker.api.timechunks.TimeChunks;
import salt.consultanttracker.api.timechunks.TimeChunksService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static salt.consultanttracker.api.client.timekeeper.Activity.CONSULTANCY_TIME;
import static salt.consultanttracker.api.client.timekeeper.Activity.REMAINING_DAYS;
import static salt.consultanttracker.api.meetings.Meetings.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class MeetingsScheduleService {

    private final ConsultantService consultantService;
    private final TimeChunksService timeChunksService;
    private final RegisteredTimeService registeredTimeService;
    private final MeetingsScheduleRepository meetingsScheduleRepository;
    private static final Logger LOGGER = Logger.getLogger(MeetingsScheduleService.class.getName());

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

    @Scheduled(cron="0 0 2 * * 4", zone = "Europe/Stockholm")
    public void assignMeetingsDatesForActiveConsultants() {
        LOGGER.info("Starting to assign meetings dates for active consultants");
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
        LOGGER.info("Meetings dates assigned for active consultants");
    }

    private void createMeetings(Consultant consultant, List<TimeChunks> timeChunks) {
        int firstMeetingWeeks = 4;
        int thirdMeetingWeeks = 8;
        // Logic for forth meeting commented out as it is not used in the current implementation
//        int fourthMeetingWeeks = 2;
        LocalDate firstMeeting = getFirstMeetingDate(timeChunks, firstMeetingWeeks);
        LocalDate secondMeeting = getSecondMeetingDate(timeChunks);
        LocalDate thirdMeeting = getThirdMeetingDate(timeChunks, thirdMeetingWeeks);
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
                consultant.getResponsiblePT()));
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

    public LocalDate getThirdMeetingDate(List<TimeChunks> timeChunks, int weeks) {
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
