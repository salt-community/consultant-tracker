package com.example.backend.registeredTime;

import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.TotalDaysStatistics;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.redDays.RedDaysService;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import com.example.backend.registeredTime.dto.RemainingDaysDto;
import com.example.backend.utils.Utilities;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.backend.client.timekeeper.Activity.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RegisteredTimeService {
    private final RegisteredTimeRepository registeredTimeRepository;
    private final ConsultantService consultantService;
    private final TimekeeperClient timekeeperClient;
    private final RedDaysService redDaysService;

    public RegisteredTimeService(RegisteredTimeRepository registeredTimeRepository,
                                 @Lazy ConsultantService consultantService,
                                 TimekeeperClient timekeeperClient,
                                 RedDaysService redDaysService) {
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
        RegisteredTime lastWorkTimeRegistered = registeredTimeRepository.findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(consultantId, CONSULTANCY_TIME.activity);
        if (lastWorkTimeRegistered != null) {
            return lastWorkTimeRegistered.getProjectName();
        }
        return PGP.activity;
    }

    public void fetchAndSaveTimeRegisteredByConsultantDB() {
        List<Consultant> activeConsultants = consultantService.getAllActiveConsultants();
        for (Consultant consultant : activeConsultants) {
            List<ConsultantTimeDto> consultantRegisteredTime = fetchTimeFromTimekeeperDB(consultant.getId(), consultant.getTimekeeperId());
            consultantRegisteredTime = filterOutIncorrectlyRegisteredTimeDB(consultantRegisteredTime);
            saveConsultantTimeDB(consultantRegisteredTime);
        }
    }

    private List<ConsultantTimeDto> fetchTimeFromTimekeeperDB(UUID consultantId, Long timekeeperId) {
        List<TimekeeperRegisteredTimeResponseDto> consultancyTime = timekeeperClient.getTimeRegisteredByConsultant(timekeeperId);
        List<ConsultantTimeDto> consultantTimeDtoList = new ArrayList<>();
        for (TimekeeperRegisteredTimeResponseDto item : consultancyTime) {
            consultantTimeDtoList.add(new ConsultantTimeDto(
                    new RegisteredTimeKey(consultantId, item.date().withHour(0).withMinute(0).withSecond(0)),
                    item.date().withHour(23).withMinute(59).withSecond(59),
                    item.activityName(),
                    item.totalHours(),
                    item.projectName()));
        }
        return consultantTimeDtoList;
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<ConsultantTimeDto> filterOutIncorrectlyRegisteredTimeDB(List<ConsultantTimeDto> consultantRegisteredTime) {
        return consultantRegisteredTime
                .stream()
                .filter(el -> el.totalHours() != 0 || !el.dayType().equals(CONSULTANCY_TIME.activity)
                        || (!redDaysService.isRedDay(el.itemId().getStartDate().toLocalDate(), el.itemId().getConsultantId())
                        && !Utilities.isWeekend(el.itemId().getStartDate().getDayOfWeek().getValue())))
                .toList();
    }

    private void saveConsultantTimeDB(List<ConsultantTimeDto> consultantTimeDtoList) {
        Map<LocalDate, ConsultantTimeDto> timelineItemsMap = createTimeItemsDB(consultantTimeDtoList);
        List<ConsultantTimeDto> timeToSave = new ArrayList<>(timelineItemsMap.values());
        for (ConsultantTimeDto t : timeToSave) {
            registeredTimeRepository
                    .save(new RegisteredTime(
                            new RegisteredTimeKey(t.itemId().getConsultantId(),
                                    t.itemId().getStartDate()),
                            t.dayType(),
                            t.endDate().withHour(23).withMinute(59).withSecond(59),
                            Math.round(t.totalHours() * 10.0) / 10.0,
                            t.projectName()
                    ));
        }
    }

    private Map<LocalDate, ConsultantTimeDto> createTimeItemsDB(List<ConsultantTimeDto> consultantTimeDtoList) {
        Map<LocalDate, ConsultantTimeDto> timeItemsMap = new HashMap<>();
        consultantTimeDtoList.forEach(consultantTimeDto -> {
            LocalDate startDate = consultantTimeDto.itemId().getStartDate().toLocalDate();
            if (timeItemsMap.containsKey(startDate)) {
                timeItemsMap.compute(startDate, (k, v) ->
                        new ConsultantTimeDto(
                                consultantTimeDto.itemId(),
                                consultantTimeDto.endDate(),
                                consultantTimeDto.dayType(),
                                consultantTimeDto.totalHours() + timeItemsMap.get(startDate).totalHours(),
                                consultantTimeDto.projectName()));
            } else {
                timeItemsMap.put(startDate, consultantTimeDto);
            }
        });
        return timeItemsMap;
    }

    public ConsultantResponseDto getConsultantTimelineItems(Consultant consultant) {
        List<RegisteredTimeResponseDto> consultantTimeDto = getGroupedConsultantsRegisteredTimeItems(consultant.getId());
        TotalDaysStatistics totalDaysStatistics = getAllDaysStatistics(consultant.getId());
        return ConsultantResponseDto.toDto(consultant, totalDaysStatistics, Objects.requireNonNullElseGet(consultantTimeDto, ArrayList::new));
    }


    private TotalDaysStatistics getAllDaysStatistics(UUID id) {
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
        int totalVacationDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, VACATION.activity).orElse(0);
        return new TotalDaysStatistics(totalRemainingDays, totalWorkedDays, totalVacationDays, totalRemainingHours, Utilities.roundToOneDecimalPoint(totalWorkedHours));
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public Double countTotalWorkedHours(UUID consultantId) {
        Double getTotalConsultancyHour = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndProjectName(consultantId, CONSULTANCY_TIME.activity).orElse(0.0);
        Double getTotalAdministrationHour = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndProjectName(consultantId, OWN_ADMINISTRATION.activity).orElse(0.0);
        return getTotalAdministrationHour + getTotalConsultancyHour;
    }

    private int countTotalWorkedDays(UUID consultantId) {
        int countOfWorkedDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, CONSULTANCY_TIME.activity).orElse(0);
        countOfWorkedDays += registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, OWN_ADMINISTRATION.activity).orElse(0);
        return countOfWorkedDays;
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<ConsultantTimeDto> getAllConsultantsTimeItems() {
        consultantService.fetchDataFromTimekeeper();
        List<Consultant> consultants = consultantService.getAllConsultants();
        List<RegisteredTime> consultantTimeDtoList = new ArrayList<>();
        for (Consultant consultant : consultants) {
            List<RegisteredTime> timeByConsultantId = getTimeByConsultantId(consultant.getId());
            consultantTimeDtoList.addAll(timeByConsultantId);
        }
        return consultantTimeDtoList
                .stream()
                .map(ConsultantTimeDto::toConsultantTimeDto)
                .toList();
    }


    public List<RegisteredTimeResponseDto> getGroupedConsultantsRegisteredTimeItems(UUID id) {
        List<RegisteredTime> registeredTimeList = getTimeByConsultantId(id);
        if (registeredTimeList.isEmpty()) {
            return null;
        }
        Map<Integer, RegisteredTimeDto> mappedRecords = new HashMap<>();
        String prevType = registeredTimeList.getFirst().getType();
        int generatedKey = 0;
        for (RegisteredTime registeredTime : registeredTimeList) {
            if (mappedRecords.containsKey(generatedKey)) {
                LocalDate startDateBefore = mappedRecords.get(generatedKey).endDate().toLocalDate();
                long daysBetween = DAYS.between(startDateBefore, registeredTime.getId().getStartDate().toLocalDate());
                boolean onlyRedsAndWeekendsBetween = redDaysService.checkRedDaysOrWeekend(daysBetween, startDateBefore, id, "check first") == (daysBetween - 1);
                if (prevType.equals(registeredTime.getType()) && onlyRedsAndWeekendsBetween) {
                    mappedRecords.computeIfPresent(generatedKey, (key, registeredTimeDto) ->
                            new RegisteredTimeDto(registeredTimeDto.startDate(),
                                    registeredTime.getEndDate(),
                                    registeredTimeDto.type(),
                                    registeredTime.getProjectName(),
                                    registeredTimeDto.days() + 1
                            ));
                } else {
                    generatedKey++;
                    mappedRecords.put(generatedKey,
                            new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                                    registeredTime.getEndDate(),
                                    registeredTime.getType(),
                                    registeredTime.getProjectName(), 1)
                    );
                }
            } else {
                mappedRecords.put(generatedKey,
                        new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                                registeredTime.getEndDate(),
                                registeredTime.getType(),
                                registeredTime.getProjectName(),
                                1));
            }
            prevType = registeredTime.getType();
        }
        Map<Integer, RegisteredTimeDto> filledGaps = fillRegisteredTimeGaps(sortMap(mappedRecords), id);

        List<RegisteredTimeDto> regTimeRespDtos = new ArrayList<>(filledGaps.values());
        List<RegisteredTimeResponseDto> registeredTimeResponseDtos = new ArrayList<>(regTimeRespDtos.stream().map(RegisteredTimeResponseDto::fromRegisteredTimeDto).toList());
        RegisteredTimeResponseDto remainingConsultancyTimeByConsultantId = getRemainingConsultancyTimeByConsultantId(id);
        if (remainingConsultancyTimeByConsultantId != null) {
            registeredTimeResponseDtos.add(remainingConsultancyTimeByConsultantId);
        }
        return registeredTimeResponseDtos;
    }

    private Map<Integer, RegisteredTimeDto> sortMap(Map<Integer, RegisteredTimeDto> resultSet) {
        return resultSet.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().startDate()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (left, right) -> left,
                        LinkedHashMap::new));
    }

    private Map<Integer, RegisteredTimeDto> fillRegisteredTimeGaps(Map<Integer, RegisteredTimeDto> resultSet, UUID consultantId) {
        Map<Integer, RegisteredTimeDto> filledGapsMap = new HashMap<>(resultSet);
        for (int i = 0; i < resultSet.size() - 1; i++) {
            LocalDate dateBefore = resultSet.get(i).endDate().toLocalDate();
            LocalDate dateAfter = resultSet.get(i + 1).startDate().toLocalDate();
            long daysBetween = DAYS.between(dateBefore, dateAfter);
            if (daysBetween > 1) {
                int nonWorkingDays = redDaysService.checkRedDaysOrWeekend(daysBetween, dateBefore, consultantId, "check first");
                addFilledGapsToMap(nonWorkingDays, daysBetween, filledGapsMap, dateBefore, dateAfter, i, consultantId);
            }
        }
        return filledGapsMap;
    }

    private void addFilledGapsToMap(int nonWorkingDays, Long daysBetween, Map<Integer, RegisteredTimeDto> filledGapsMap, LocalDate dateBefore, LocalDate dateAfter, int i, UUID id) {
        if (nonWorkingDays != 0) {
            filledGapsMap.computeIfPresent(i, (key, value) ->
                    new RegisteredTimeDto(value.startDate(),
                            value.endDate().plusDays(nonWorkingDays),
                            value.type(),
                            value.projectName(),
                            value.days()));
        }
        if (nonWorkingDays != daysBetween - 1) {
            int totalNonWorkingDays = redDaysService.checkRedDaysOrWeekend(daysBetween, dateBefore, id, "check all");
            filledGapsMap.put(filledGapsMap.size(),
                    new RegisteredTimeDto(dateBefore.plusDays(nonWorkingDays + 1).atStartOfDay(),
                            dateAfter.minusDays(1).atTime(23, 59, 59),
                            "No Registered Time", "No Registered Time", (int) (daysBetween - 1 - totalNonWorkingDays)));
        }
    }


    public RegisteredTimeResponseDto getRemainingConsultancyTimeByConsultantId(UUID consultantId) {
        LocalDateTime lastRegisteredDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId).getEndDate();
        //TODO fix PGP
        LocalDateTime startDate = lastRegisteredDate.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        RemainingDaysDto estimatedEndDate = getEstimatedConsultancyEndDate(consultantId, startDate);
        if (estimatedEndDate.endDate() == startDate) {
            return null;
        }
        return new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                startDate,
                estimatedEndDate.endDate(),
                "Remaining Days",
                "Remaining Days",
                estimatedEndDate.remainingDays());
    }

    private RemainingDaysDto getEstimatedConsultancyEndDate(UUID consultantId, LocalDateTime startDate) {
        Double totalWorkedHours = countTotalWorkedHours(consultantId);
        String countryCode = consultantService.getCountryCodeByConsultantId(consultantId);
        int remainingConsultancyDays = (int) Utilities.countRemainingDays(totalWorkedHours, countryCode);
        if (remainingConsultancyDays <= 0) {
            return new RemainingDaysDto(startDate, remainingConsultancyDays);
        }
        return new RemainingDaysDto(redDaysService.removeNonWorkingDays(startDate, remainingConsultancyDays, consultantId), remainingConsultancyDays);
    }
}