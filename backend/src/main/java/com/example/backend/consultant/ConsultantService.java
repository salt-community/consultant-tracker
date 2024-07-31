package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.registeredTime.RegisteredTime;
import com.example.backend.registeredTime.RegisteredTimeKey;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.backend.client.Activity.CONSULTANCY_TIME;
import static java.time.temporal.ChronoUnit.DAYS;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;

    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize);
        List<ConsultantResponseDto> consultantsDto = consultantsList.stream()
                .map(this::getConsultantsRegisteredTimeItems).toList();
        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultantsDto);
    }

//    public List<RegisteredTimeResponseDto> getConsultantTimeDto(List<RegisteredTime> consultantTimeDtoList) {
//        List<RegisteredTimeResponseDto> listOfRegisteredTime = new ArrayList<>();
//        UUID consultantId = consultantTimeDtoList.getFirst().getId().getConsultantId();
//        listOfRegisteredTime.add(getConsultancyTimeItemByConsultantId(consultantId));
//        consultantTimeDtoList.stream().map(el -> getGroupedConsultantsRegisteredTimeItems(el.getId().getConsultantId()));
//        // ACCOUNT FOR RED DAYS
//        RegisteredTimeResponseDto remainingConsultancyTimeByConsultantId = registeredTimeService.getRemainingConsultancyTimeByConsultantId(consultantId);
//        if (remainingConsultancyTimeByConsultantId != null) {
//            listOfRegisteredTime.add(remainingConsultancyTimeByConsultantId);
//        }
//        return listOfRegisteredTime;
//    }

//    private RegisteredTimeResponseDto getConsultancyTimeItemByConsultantId(UUID consultantId) {
//        List<RegisteredTime> firstAndLastRegisteredDateByConsultantId =
//                registeredTimeService.getFirstAndLastDateByConsultantId(consultantId);
//        return new RegisteredTimeResponseDto(
//                UUID.randomUUID(),
//                firstAndLastRegisteredDateByConsultantId.get(0).getId().getStartDate(),
//                firstAndLastRegisteredDateByConsultantId.get(1).getEndDate(),
//                CONSULTANCY_TIME.activity,
//                );
//    }

    public Page<Consultant> getAllConsultantsPageable(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return consultantRepository.findAllByActiveTrue(pageRequest);
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //    @Scheduled(cron = "0 0 0 * * *")
    public void fetchDataFromTimekeeper() {
        List<TimekeeperUserDto> timekeeperUserDto = timekeeperClient.getUsers();
        assert timekeeperUserDto != null;
        List<Long> timekeeperIdsToAdd = checkTimekeeperUsersWithDatabase(timekeeperUserDto);
        if (!timekeeperIdsToAdd.isEmpty()) {
            timekeeperIdsToAdd.forEach(id -> {
                TimekeeperUserDto tkUser = timekeeperUserDto.stream()
                        .filter(u -> Objects.equals(u.id(), id)).findFirst().orElse(null);
                if (tkUser != null) {
                    Consultant consultant = new Consultant(
                            UUID.randomUUID(),
                            tkUser.firstName().trim().concat(" ").concat(tkUser.lastName().trim()),
                            tkUser.email(),
                            tkUser.phone(),
                            id,
                            tkUser.isActive()
                    );
                    createConsultant(consultant);
                }
            });
        }
        fetchRecordedTimeForConsultant();
    }

    private void fetchRecordedTimeForConsultant() {
        List<Consultant> consultants = getAllActiveConsultants();
        for (Consultant consultant : consultants) {
            List<ConsultantTimeDto> consultantRegisteredTime = getConsultantTimeDto(consultant.getId(), consultant.getTimekeeperId());
            registeredTimeService.saveConsultantTime(consultantRegisteredTime);
        }
    }

    private List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    private List<Long> checkTimekeeperUsersWithDatabase(List<TimekeeperUserDto> timekeeperUserResponseDto) {
        List<Long> idsToAdd = new ArrayList<>();
        List<Consultant> consultants = getAllConsultants();
        timekeeperUserResponseDto.forEach(tkUser -> {
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                idsToAdd.add(tkUser.id());
            } else {
                consultants.stream()
                        .filter(consultant -> consultant.getTimekeeperId().equals(tkUser.id()))
                        .forEach(consultant -> {
                            if (consultant.isActive() != tkUser.isActive() || consultant.isActive() != tkUser.isEmployee()) {
                                consultant.setActive(tkUser.isActive() && tkUser.isEmployee());
                                consultantRepository.save(consultant);
                            }
                        });
            }
        });
        return idsToAdd;
    }

    private void createConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    public ConsultantResponseDto getConsultantsRegisteredTimeItems(Consultant consultant) {
        List<RegisteredTimeResponseDto> consultantTimeDto = getGroupedConsultantsRegisteredTimeItems(consultant.getId());
        return ConsultantResponseDto.toDto(consultant, consultantTimeDto);
    }

    public Double getConsultancyHoursByUserId(UUID id) {
        List<RegisteredTime> consultancyTime = registeredTimeService.getTimeByConsultantId(id);
        AtomicReference<Double> totalHoursResponse = new AtomicReference<>(0.0);
        consultancyTime.stream()
                .filter(el -> el.getType().equals(CONSULTANCY_TIME.activity))
                .forEach(el -> totalHoursResponse.updateAndGet(v -> v + el.getTotalHours()));
        return totalHoursResponse.get();
    }

    public List<ConsultantTimeDto> getAllConsultantsTimeItems() {
        fetchDataFromTimekeeper();
        List<Consultant> consultants = getAllConsultants();
        List<RegisteredTime> consultantTimeDtoList = new ArrayList<>();
        for (Consultant consultant : consultants) {
            List<RegisteredTime> timeByConsultantId = registeredTimeService.getTimeByConsultantId(consultant.getId());
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
        List<RegisteredTime> registeredTimeList = registeredTimeService.getTimeByConsultantId(id);
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
                    if (registeredTimeService.isWeekend(dateBefore.plusDays(j).getDayOfWeek().getValue())) {
                        weekend++;
                        continue;
                    }
                    resultSet.put(resultSet.size(),
                            new RegisteredTimeDto(dateBefore.plusDays(weekend).atStartOfDay(),
                                    dateAfter.minusDays(1).atTime(23, 59, 59),
                                    "PGP", "PGP"));
                    j = daysBetween;
                }
            }
        }
        return resultSet;
    }
}
