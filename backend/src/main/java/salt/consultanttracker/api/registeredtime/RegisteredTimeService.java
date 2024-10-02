package salt.consultanttracker.api.registeredtime;

import org.springframework.cache.annotation.Cacheable;
import salt.consultanttracker.api.client.timekeeper.TimekeeperClient;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperRegisteredTimeResponseDto;
import salt.consultanttracker.api.consultant.Consultant;
import salt.consultanttracker.api.consultant.ConsultantService;

import salt.consultanttracker.api.consultant.dto.TotalDaysStatisticsDto;
import salt.consultanttracker.api.reddays.RedDayService;
import salt.consultanttracker.api.registeredtime.dto.RemainingDaysDto;
import salt.consultanttracker.api.timechunks.TimeChunks;
import salt.consultanttracker.api.timechunks.TimeChunksKey;
import salt.consultanttracker.api.utils.Utilities;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static salt.consultanttracker.api.client.timekeeper.Activity.*;

@Service
public class RegisteredTimeService {
    private final RegisteredTimeRepository registeredTimeRepository;
    private final ConsultantService consultantService;
    private final TimekeeperClient timekeeperClient;
    private final RedDayService redDaysService;

    public RegisteredTimeService(RegisteredTimeRepository registeredTimeRepository,
                                 @Lazy ConsultantService consultantService,
                                 TimekeeperClient timekeeperClient,
                                 RedDayService redDaysService) {
        this.registeredTimeRepository = registeredTimeRepository;
        this.consultantService = consultantService;
        this.timekeeperClient = timekeeperClient;
        this.redDaysService = redDaysService;
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<RegisteredTime> getTimeByConsultantId(UUID id) {
        return registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(id);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public String getCurrentClient(UUID consultantId) {
        RegisteredTime lastWorkTimeRegistered = registeredTimeRepository
                .findFirstById_ConsultantIdOrderByEndDateDesc(consultantId);
        if (lastWorkTimeRegistered != null) {
            return lastWorkTimeRegistered.getProjectName();
        }
        return PGP.activity;
    }

    //-----------------------------------MOVE TO SEPARATE SERVICE--------------------------------------------
    public void fetchAndSaveTimeRegisteredByConsultantDB(List<Consultant> consultants) {
        for (Consultant consultant : consultants) {
            List<RegisteredTime> consultantRegisteredTime =
                    fetchTimeFromTimekeeperDB(consultant.getId(), consultant.getTimekeeperId());
            consultantRegisteredTime = filterOutIncorrectlyRegisteredTimeDB(consultantRegisteredTime);
            if(!consultantRegisteredTime.isEmpty()) {
                saveConsultantTimeDB(consultantRegisteredTime);
            }
        }
    }

    private List<RegisteredTime> fetchTimeFromTimekeeperDB(UUID consultantId, Long timekeeperId) {
        long countRegisteredTime = registeredTimeRepository.countAllById_ConsultantId(consultantId);
        List<TimekeeperRegisteredTimeResponseDto> consultancyTime =
                timekeeperClient.getTimeRegisteredByConsultant(timekeeperId, countRegisteredTime);
        List<RegisteredTime> registeredTimeList = new ArrayList<>();
        if(consultancyTime.isEmpty()){
            return registeredTimeList;
        }
        for (TimekeeperRegisteredTimeResponseDto item : consultancyTime) {
            registeredTimeList.add(new RegisteredTime(
                    new RegisteredTimeKey(consultantId, item.date().withHour(0).withMinute(0).withSecond(0)),
                    item.activityName(),
                    item.date().withHour(23).withMinute(59).withSecond(59),
                    item.totalHours(),
                    item.projectName()));
        }
        return registeredTimeList;
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<RegisteredTime> filterOutIncorrectlyRegisteredTimeDB(List<RegisteredTime> consultantRegisteredTime) {
        return consultantRegisteredTime
                .stream()
                .filter(el -> el.getTotalHours() != 0
                        || (!el.getType().equals(CONSULTANCY_TIME.activity)
                        && !el.getType().equals(OWN_ADMINISTRATION.activity)))
                .toList();
    }

    private void saveConsultantTimeDB(List<RegisteredTime> consultantTimeDtoList) {
        registeredTimeRepository.saveAll(createTimeItemsDB(consultantTimeDtoList));
    }

    private List<RegisteredTime> createTimeItemsDB(List<RegisteredTime> consultantTimeDtoList) {
        Map<LocalDate, RegisteredTime> timeItemsMap = new HashMap<>();
        consultantTimeDtoList.forEach(registeredTime -> {
            LocalDate startDate = registeredTime.getId().getStartDate().toLocalDate();
            if (timeItemsMap.containsKey(startDate)) {
                timeItemsMap.compute(startDate, (k, v) ->
                        new RegisteredTime(
                                registeredTime.getId(),
                                registeredTime.getType(),
                                registeredTime.getEndDate(),
                                registeredTime.getTotalHours() + timeItemsMap.get(startDate).getTotalHours(),
                                registeredTime.getProjectName()));
            } else {
                timeItemsMap.put(startDate, registeredTime);
            }
        });
        return new ArrayList<>(timeItemsMap.values());
    }

    @Cacheable(cacheNames = "totalStatistics", key="#id")
    public TotalDaysStatisticsDto getAllDaysStatistics(UUID id) {
        String country = consultantService.getCountryCodeByConsultantId(id);
        int totalWorkedDays = countTotalWorkedDays(id);
        double totalRemainingDays = Utilities.getTotalDaysByCountry(country);
        double totalRemainingHours = Utilities.getTotalHours(country);
        double totalWorkedHours = 0.0;
        if (totalWorkedDays != 0) {
            totalWorkedHours = countTotalWorkedHours(id);
            totalRemainingDays = Utilities.roundToTwoDecimalPoints(Utilities.countRemainingDays(totalWorkedHours, country));
            totalRemainingHours = Utilities.roundToOneDecimalPoint(totalRemainingDays * Utilities.getStandardWorkingHours(country));
        }
        int totalVacationDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, VACATION.activity)
                .orElse(0);
        int totalSickDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, SICK_LEAVE.activity)
                .orElse(0);
        int totalParentalLeaveDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, PARENTAL_LEAVE.activity)
                .orElse(0);
        int totalVABDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, SICK_CHILD_CARE.activity)
                .orElse(0);
        int totalUnpaidVacationDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, LEAVE_OF_ABSENCE.activity)
                .orElse(0);
        return new TotalDaysStatisticsDto(totalRemainingDays,
                totalWorkedDays,
                totalVacationDays,
                totalSickDays,
                totalParentalLeaveDays,
                totalVABDays,
                totalUnpaidVacationDays,
                totalRemainingHours,
                Utilities.roundToOneDecimalPoint(totalWorkedHours));
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public Double countTotalWorkedHours(UUID consultantId) {
        Double getTotalConsultancyHour = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(consultantId, CONSULTANCY_TIME.activity).orElse(0.0);
        Double getTotalAdministrationHour = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(consultantId, OWN_ADMINISTRATION.activity).orElse(0.0);
        return getTotalAdministrationHour + getTotalConsultancyHour;
    }

    private int countTotalWorkedDays(UUID consultantId) {
        int countOfWorkedDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, CONSULTANCY_TIME.activity).orElse(0);
        countOfWorkedDays += registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, OWN_ADMINISTRATION.activity).orElse(0);
        return countOfWorkedDays;
    }

    public TimeChunks getRemainingConsultancyTimeByConsultantId(UUID consultantId) {
        LocalDateTime lastRegisteredDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId).getEndDate();
        LocalDateTime startDate = lastRegisteredDate.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        RemainingDaysDto estimatedEndDate = getEstimatedConsultancyEndDate(consultantId, startDate);
        if (estimatedEndDate.endDate() == startDate) {
            return null;
        }
        return new TimeChunks(
                new TimeChunksKey(consultantId, startDate),
                "Remaining Days",
                estimatedEndDate.endDate(),
                estimatedEndDate.remainingDays(),
                "Remaining Days");
    }

    private RemainingDaysDto getEstimatedConsultancyEndDate(UUID consultantId, LocalDateTime startDate) {
        double totalWorkedHours = countTotalWorkedHours(consultantId);
        String countryCode = consultantService.getCountryCodeByConsultantId(consultantId);
        int remainingConsultancyDays = (int) Utilities.countRemainingDays(totalWorkedHours, countryCode);
        if (remainingConsultancyDays <= 0) {
            return new RemainingDaysDto(startDate, remainingConsultancyDays);
        }
        return new RemainingDaysDto(redDaysService.removeNonWorkingDays(startDate, remainingConsultancyDays, consultantId), remainingConsultancyDays);
    }

    public List<String> getClientsByConsultantId(UUID consultantId) {
        return registeredTimeRepository.findDistinctProjectNameBydId_ConsultantIdOrderById_StartDateAsc(consultantId);
    }

    public LocalDate getStartDateByClientAndConsultantId(String client, UUID consultantId) {
        return registeredTimeRepository.findFirstByProjectNameAndId_ConsultantIdOrderById_StartDateAsc(client, consultantId)
                .getId().getStartDate().toLocalDate();
    }

    public LocalDate getEndDateByClientAndConsultantId(String client, UUID consultantId) {
        return registeredTimeRepository.findFirstByProjectNameAndId_ConsultantIdOrderByEndDateDesc(client, consultantId)
                .getEndDate().toLocalDate();
    }
}