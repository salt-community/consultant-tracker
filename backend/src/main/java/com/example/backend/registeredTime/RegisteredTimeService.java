package com.example.backend.registeredTime;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.redDays.RedDaysService;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.backend.client.Activity.CONSULTANCY_TIME;
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

    public void saveConsultantTime(List<ConsultantTimeDto> consultantTimeDtoList) {
        AtomicReference<LocalDateTime> startTime = new AtomicReference<>(null);
        for (ConsultantTimeDto consultantTimeDto : consultantTimeDtoList) {
            if (startTime.get() == null) {
                startTime.set(consultantTimeDto.itemId().getStartDate());
            } else if (startTime.get().getDayOfMonth() == consultantTimeDto.itemId().getStartDate().getDayOfMonth() &&
                    startTime.get().getMonth() == consultantTimeDto.itemId().getStartDate().getMonth() &&
                    startTime.get().getYear() == consultantTimeDto.itemId().getStartDate().getYear()
            ) {
                continue;
            }
            registeredTimeRepository.save(new RegisteredTime(
                    new RegisteredTimeKey(consultantTimeDto.itemId().getConsultantId(),
                            consultantTimeDto.itemId().getStartDate()),
                    consultantTimeDto.dayType(),
                    consultantTimeDto.endDate().withHour(23).withMinute(59).withSecond(59),
                    8.0,
                    consultantTimeDto.projectName()
            ));
        }
    }

    public void fetchRecordedTimeForConsultant() {
        List<Consultant> consultants = consultantService.getAllActiveConsultants();
        for (Consultant consultant : consultants) {
            List<ConsultantTimeDto> consultantRegisteredTime = getConsultantTimeDto(consultant.getId(), consultant.getTimekeeperId());
            saveConsultantTime(consultantRegisteredTime);
        }
    }

    public ConsultantResponseDto getConsultantsRegisteredTimeItems(Consultant consultant) {
        List<RegisteredTimeResponseDto> consultantTimeDto = getGroupedConsultantsRegisteredTimeItems(consultant.getId());
        return ConsultantResponseDto.toDto(consultant, consultantTimeDto);
    }

    public Double getConsultancyHoursByUserId(UUID id) {
        List<RegisteredTime> consultancyTime = getTimeByConsultantId(id);
        AtomicReference<Double> totalHoursResponse = new AtomicReference<>(0.0);
        consultancyTime.stream()
                .filter(el -> el.getType().equals(CONSULTANCY_TIME.activity))
                .forEach(el -> totalHoursResponse.updateAndGet(v -> v + el.getTotalHours()));
        return totalHoursResponse.get();
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
                    //TODO overtime
                    8, item.projectName()));
        }
        return consultantTimeDtoList;
    }

    public List<RegisteredTimeResponseDto> getGroupedConsultantsRegisteredTimeItems(UUID id) {
        List<RegisteredTime> registeredTimeList = getTimeByConsultantId(id);
        Map<Integer, RegisteredTimeDto> mappedRecords = new HashMap<>();
        String prevType = registeredTimeList.get(0).getType();
        int generatedKey = 0;
        for (RegisteredTime registeredTime : registeredTimeList) {
            if (mappedRecords.containsKey(generatedKey)) {
                if (prevType.equals(registeredTime.getType())) {
                    mappedRecords.computeIfPresent(generatedKey, (key, registeredTimeDto) ->
                            new RegisteredTimeDto(registeredTimeDto.startDate(),
                                    registeredTime.getEndDate(),
                                    registeredTimeDto.type(),
                                    registeredTime.getProjectName()
                            ));
                } else {
                    generatedKey++;
                    mappedRecords.put(generatedKey,
                            new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                                    registeredTime.getEndDate(),
                                    registeredTime.getType(),
                                    registeredTime.getProjectName()));
                }
            } else {
                mappedRecords.put(generatedKey,
                        new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                                registeredTime.getEndDate(),
                                registeredTime.getType(),
                                registeredTime.getProjectName()));
            }
            prevType = registeredTime.getType();
        }
        Map<Integer, RegisteredTimeDto> filledGaps = fillRegisteredTimeGaps(sortMap(mappedRecords));

        return convertToList(sortMap(filledGaps));
    }

    private List<RegisteredTimeResponseDto> convertToList(Map<Integer, RegisteredTimeDto> resultSet) {
        return resultSet.values()
                .stream()
                .map(RegisteredTimeResponseDto::fromRegisteredTimeDto)
                .toList();
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

    private Map<Integer, RegisteredTimeDto> fillRegisteredTimeGaps(Map<Integer, RegisteredTimeDto> resultSet) {
        for (int i = 0; i < resultSet.size() - 1; i++) {
            LocalDate dateBefore = resultSet.get(i).endDate().toLocalDate();
            LocalDate dateAfter = resultSet.get(i + 1).startDate().toLocalDate();
            long daysBetween = DAYS.between(dateBefore, dateAfter);
            if (daysBetween > 1) {
                for (long j = 1; j < daysBetween; j++) {
                    int weekend = 1;
                    var dateToCheckForRedDay = dateBefore.plusDays(j);
                    boolean isRedDay = isRedDay(dateToCheckForRedDay);
                    System.out.println("dateToCheckForRedDay = " + dateToCheckForRedDay);
                    System.out.println("isRedDay = " + isRedDay);
                    if (isWeekend(dateBefore.plusDays(j).getDayOfWeek().getValue())
                    || isRedDay(dateBefore.plusDays(j))) {
                        weekend++;
                        continue;
                    }
                    resultSet.put(resultSet.size(),
                            new RegisteredTimeDto(dateBefore.plusDays(weekend).atStartOfDay(),
                                    dateAfter.minusDays(1).atTime(23, 59, 59),
                                    "No Registered Time", "No Registered Time"));
                    j = daysBetween;
                }
            }
        }
        return resultSet;
    }

    public List<RegisteredTime> getTimeByConsultantId(UUID id) {
        return registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(id);
    }

    public List<RegisteredTime> getFirstAndLastDateByConsultantId(UUID consultantId) {
        RegisteredTime startDate = registeredTimeRepository.findFirstById_ConsultantIdOrderById_StartDateAsc(consultantId);
        RegisteredTime endDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId);
        return new ArrayList<>(Arrays.asList(startDate, endDate));
    }


//    public RegisteredTimeResponseDto getRemainingConsultancyTimeByConsultantId(UUID consultantId) {
//        LocalDateTime lastRegisteredDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId).getEndDate();
//        LocalDateTime startDate = lastRegisteredDate.plusDays(1).withHour(0).withMinute(0).withSecond(0);
//        LocalDateTime estimatedEndDate = getEstimatedConsultancyEndDate(consultantId, startDate);
//        if (estimatedEndDate == startDate) {
//            return null;
//        }
//        return new RegisteredTimeResponseDto(
//                UUID.randomUUID(),
//                startDate,
//                estimatedEndDate,
//                "Remaining Days");
//    }
    private LocalDateTime getEstimatedConsultancyEndDate(UUID consultantId, LocalDateTime startDate) {
        final int REQUIRED_HOURS = 2024;
        int countOfWorkedDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, CONSULTANCY_TIME.activity);
        int remainingConsultancyDays = REQUIRED_HOURS / 8 - countOfWorkedDays;
        if (remainingConsultancyDays <= 0) {
            return startDate;
        }
        return accountForNonWorkingDays(startDate, remainingConsultancyDays);
    }

    private LocalDateTime accountForNonWorkingDays(LocalDateTime startDate, int remainingDays) {
        if (remainingDays <= 0) {
            return startDate;
        }
        int daysCountDown = remainingDays;
        int i = 0;
        while (daysCountDown > 0) {

            if (!isWeekend(startDate.plusDays(i).getDayOfWeek().getValue()) /*&& !isRedDay(startDate.plusDays(i))*/) {
                daysCountDown--;
            }
            i++;
        }
        LocalDateTime endingDate = startDate.plusDays(i).minusSeconds(1L);
        return endingDate;
    }

    public boolean isWeekend(int day) {
        final int SATURDAY = 6;
        final int SUNDAY = 7;
        return day == SATURDAY || day == SUNDAY;
    }

    private boolean isRedDay(LocalDate date) {
        List<LocalDate> redDays = redDaysService.getRedDays();
        return redDays.contains(date);
    }
}