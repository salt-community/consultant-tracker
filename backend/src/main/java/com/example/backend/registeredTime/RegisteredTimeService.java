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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

    //    public void saveConsultantTime(List<ConsultantTimeDto> consultantTimeDtoList) {
//        AtomicReference<LocalDateTime> startTime = new AtomicReference<>(consultantTimeDtoList.get(0).itemId().getStartDate());
//        double hours = 0;
//        for (ConsultantTimeDto consultantTimeDto : consultantTimeDtoList) {
//            if (startTime.get().getDayOfMonth() == consultantTimeDto.itemId().getStartDate().getDayOfMonth()
//                    && startTime.get().getMonth() == consultantTimeDto.itemId().getStartDate().getMonth()
//                    && startTime.get().getYear() == consultantTimeDto.itemId().getStartDate().getYear()
//            ) {
//                hours += consultantTimeDto.totalHours();
//                continue;
//            } else {
//                registeredTimeRepository.save(new RegisteredTime(
//                        new RegisteredTimeKey(consultantTimeDto.itemId().getConsultantId(),
//                                consultantTimeDto.itemId().getStartDate()),
//                        consultantTimeDto.dayType(),
//                        consultantTimeDto.endDate().withHour(23).withMinute(59).withSecond(59),
//                        hours,
//                        consultantTimeDto.projectName()
//                ));
//            }
//            hours = 0;
//        }
//    }
    public void saveConsultantTime(List<ConsultantTimeDto> consultantTimeDtoList) {
//        if (consultantTimeDtoList != null && !consultantTimeDtoList.isEmpty() && (consultantTimeDtoList.getFirst().itemId().getConsultantId().toString()).equals("ef2f56d0-2284-4a4d-99cf-8c8782b1495e")) {
//            System.out.println("consultantTimeDtoList = " + consultantTimeDtoList);
//        }
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
        List<ConsultantTimeDto> timeToSave = new ArrayList<>(timeItemsMap.values());
        timeToSave.forEach(t -> registeredTimeRepository.save(new RegisteredTime(
                new RegisteredTimeKey(t.itemId().getConsultantId(),
                        t.itemId().getStartDate()),
                t.dayType(),
                t.endDate().withHour(23).withMinute(59).withSecond(59),
                Math.round(t.totalHours() * 10.0) / 10.0,
                t.projectName()
        )));
    }


    public void fetchRecordedTimeForConsultant() {
        List<Consultant> consultants = consultantService.getAllActiveConsultants();
        for (Consultant consultant : consultants) {
            System.out.println("consultant.getFullName() = " + consultant.getFullName());
            List<ConsultantTimeDto> consultantRegisteredTime = getConsultantTimeDto(consultant.getId(), consultant.getTimekeeperId());
            saveConsultantTime(consultantRegisteredTime);
        }
    }

    public ConsultantResponseDto getConsultantsRegisteredTimeItems(Consultant consultant) {
        List<RegisteredTimeResponseDto> consultantTimeDto = getGroupedConsultantsRegisteredTimeItems(consultant.getId());
        TotalDaysStatistics totalDaysStatistics = getAllDaysStatistics(consultant.getId());
        return ConsultantResponseDto.toDto(consultant, totalDaysStatistics, consultantTimeDto);
    }

    private TotalDaysStatistics getAllDaysStatistics(UUID id) {
        int totalWorkedDays = countOfWorkedDays(id);
        int totalVacationDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(id, VACATION.activity);
        int totalRemainingDays = countRemainingDays(totalWorkedDays);
        return new TotalDaysStatistics(totalRemainingDays, totalWorkedDays, totalVacationDays);
    }

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
                .map(el -> new ConsultantTimeDto(
                        el.getId(),
                        el.getEndDate(),
                        el.getType(),
                        el.getTotalHours(),
                        el.getProjectName()
                ))
                .toList();
    }

    private List<ConsultantTimeDto> getConsultantTimeDto(UUID consultantId, Long timekeeperId) {
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

    public List<RegisteredTimeResponseDto> getGroupedConsultantsRegisteredTimeItems(UUID id) {
        if (id.toString().equals("cc77ba1c-cdae-4b71-9bbf-8f6c2fad22db")) {

        List<RegisteredTime> registeredTimeList = getTimeByConsultantId(id);
        Map<Integer, RegisteredTimeDto> mappedRecords = new HashMap<>();
        String prevType = registeredTimeList.getFirst().getType();
        int generatedKey = 0;
        for (RegisteredTime registeredTime : registeredTimeList) {
            if (mappedRecords.containsKey(generatedKey)) {
                LocalDate startDateBefore = mappedRecords.get(generatedKey).endDate().toLocalDate();
                long daysBetween = DAYS.between(startDateBefore, registeredTime.getId().getStartDate().toLocalDate());
                boolean onlyRedsAndWeekendsBetween = checkRedDaysOrWeekend(daysBetween, startDateBefore, id, "check first") == (daysBetween - 1);
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
                                    registeredTime.getProjectName(),1)
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
        return null;
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
                int nonWorkingDays = checkRedDaysOrWeekend(daysBetween, dateBefore, consultantId, "check first");
                addFilledGapsToMap(nonWorkingDays, daysBetween, filledGapsMap, dateBefore, dateAfter, i, consultantId);
            }
        }
        return filledGapsMap;
    }

    private int checkRedDaysOrWeekend(Long daysBetween, LocalDate dateBefore, UUID consultantId, String variant) {
        int nonWorkingDays = 0;
        for (long j = 1; j < daysBetween; j++) {
            var dateToCheck = dateBefore.plusDays(j);
            if (isWeekend(dateToCheck.getDayOfWeek().getValue())
                    || isRedDay(dateToCheck, consultantId)) {
                nonWorkingDays++;
                continue;
            }
            if(variant.equals("check first")){
                j = daysBetween;
            }
        }
        return nonWorkingDays;
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
            int totalNonWorkingDays = checkRedDaysOrWeekend(daysBetween, dateBefore, id, "check all");
            filledGapsMap.put(filledGapsMap.size(),
                    new RegisteredTimeDto(dateBefore.plusDays(nonWorkingDays + 1).atStartOfDay(),
                            dateAfter.minusDays(1).atTime(23, 59, 59),
                            "No Registered Time", "No Registered Time", (int) (daysBetween - 1 - totalNonWorkingDays)));
        }
    }


    public List<RegisteredTime> getTimeByConsultantId(UUID id) {
        return registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(id);
    }


    public RegisteredTimeResponseDto getRemainingConsultancyTimeByConsultantId(UUID consultantId) {
        LocalDateTime lastRegisteredDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId).getEndDate();
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
        int countOfWorkedDays = countOfWorkedDays(consultantId);
        int remainingConsultancyDays = countRemainingDays(countOfWorkedDays);
        if (remainingConsultancyDays <= 0) {
            return new RemainingDaysDto(startDate, remainingConsultancyDays);
        }
        return new RemainingDaysDto(accountForNonWorkingDays(startDate, remainingConsultancyDays, consultantId), remainingConsultancyDays);
    }

    private int countRemainingDays(int countOfWorkedDays) {
        final int REQUIRED_HOURS = 2024;
        return REQUIRED_HOURS / 8 - countOfWorkedDays;
    }

    private int countOfWorkedDays(UUID consultantId) {
        int countOfWorkedDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, CONSULTANCY_TIME.activity);
        countOfWorkedDays += registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, OWN_ADMINISTRATION.activity);
        return countOfWorkedDays;
    }

    private LocalDateTime accountForNonWorkingDays(LocalDateTime startDate, int remainingDays, UUID consultantId) {
        if (remainingDays <= 0) {
            return startDate;
        }
        int daysCountDown = remainingDays;
        int i = 0;
        while (daysCountDown > 0) {
            if (!isWeekend(startDate.plusDays(i).getDayOfWeek().getValue())
                    && !isRedDay(LocalDate.from(startDate.plusDays(i)), consultantId)) {
                daysCountDown--;
            }
            i++;
        }
        return startDate.plusDays(i).minusSeconds(1L);
    }

    public boolean isWeekend(int day) {
        final int SATURDAY = 6;
        final int SUNDAY = 7;
        return day == SATURDAY || day == SUNDAY;
    }

    private boolean isRedDay(LocalDate date, UUID consultantId) {
        String countryCode = consultantService.getCountryCodeByConsultantId(consultantId).trim().equals("Sverige") ? "SE" : "NO";
        List<LocalDate> redDays = redDaysService.getRedDays(countryCode);
        return redDays.contains(date);
    }
}