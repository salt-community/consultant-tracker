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
import java.time.LocalDateTime;
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
        //TODO remove filter later
        List<ConsultantResponseDto> consultants = consultantsList.stream()
//                .filter(el-> el.getTimekeeperId() == 22784)
                .map(el -> findConsultantDtoById(el.getId())).toList();
        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultants);

    }

    public List<RegisteredTimeResponseDto> getConsultantTimeDto(List<RegisteredTime> consultantTimeDtoList) {
        List<RegisteredTimeResponseDto> listOfRegisteredTime = new ArrayList<>();
        UUID consultantId = consultantTimeDtoList.getFirst().getId().getConsultantId();
        listOfRegisteredTime.add(getConsultancyTimeItemByConsultantId(consultantId));
        listOfRegisteredTime.addAll(getOtherRegisteredTimeByConsultantId(consultantTimeDtoList));
        // ACCOUNT FOR RED DAYS
        RegisteredTimeResponseDto remainingConsultancyTimeByConsultantId = registeredTimeService.getRemainingConsultancyTimeByConsultantId(consultantId);
        if (remainingConsultancyTimeByConsultantId != null) {
            listOfRegisteredTime.add(remainingConsultancyTimeByConsultantId);
        }

        return listOfRegisteredTime;
    }

    private Collection<RegisteredTimeResponseDto> getOtherRegisteredTimeByConsultantId(List<RegisteredTime> consultantTimeDtoList) {
        List<RegisteredTimeResponseDto> listOfRegisteredTime = new ArrayList<>();
        AtomicReference<String> activityTypePrev = new AtomicReference<>(null);
        AtomicReference<LocalDateTime> startTime = new AtomicReference<>(null);
        AtomicReference<LocalDateTime> endTime = new AtomicReference<>();

        for (int i = 0; i < consultantTimeDtoList.size(); i++) {
            RegisteredTime consultantTimeDtoEl = consultantTimeDtoList.get(i);
            if (i == 0) {
                startTime.set(consultantTimeDtoEl.getId().getStartDate());
                endTime.set(consultantTimeDtoEl.getEndDate());
                activityTypePrev.set(consultantTimeDtoEl.getType());
                continue;
            }

            if (consultantTimeDtoEl.getType().equals(activityTypePrev.get())) {
                if (consultantTimeDtoEl.getType().equals(CONSULTANCY_TIME.activity)) {
                    continue;
                }
                if (i == consultantTimeDtoList.size() - 1) {
                    listOfRegisteredTime.add(new RegisteredTimeResponseDto(
                            UUID.randomUUID(),
                            startTime.get(),
                            consultantTimeDtoEl.getEndDate(),
                            consultantTimeDtoEl.getType()));
                    continue;
                }
            } else {
                if (!activityTypePrev.get().equals(CONSULTANCY_TIME.activity)) {
                    listOfRegisteredTime.add(new RegisteredTimeResponseDto(
                            UUID.randomUUID(),
                            startTime.get(),
                            endTime.get(),
                            activityTypePrev.get()));
                }
                if (i == consultantTimeDtoList.size() - 1) {
                    listOfRegisteredTime.add(new RegisteredTimeResponseDto(
                            UUID.randomUUID(),
                            consultantTimeDtoEl.getId().getStartDate(),
                            consultantTimeDtoEl.getEndDate(),
                            consultantTimeDtoEl.getType()));
                    continue;
                }
                startTime.set(consultantTimeDtoEl.getId().getStartDate());
                activityTypePrev.set(consultantTimeDtoEl.getType());
            }
            endTime.set(consultantTimeDtoEl.getEndDate());
        }
        return listOfRegisteredTime;
    }

    private RegisteredTimeResponseDto getConsultancyTimeItemByConsultantId(UUID consultantId) {
        List<RegisteredTime> firstAndLastRegisteredDateByConsultantId =
                registeredTimeService.getFirstAndLastDateByConsultantId(consultantId);
        return new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                firstAndLastRegisteredDateByConsultantId.get(0).getId().getStartDate(),
                firstAndLastRegisteredDateByConsultantId.get(1).getEndDate(),
                CONSULTANCY_TIME.activity);
    }

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

    public Consultant findConsultantById(UUID id) {
        return consultantRepository.findById(id).orElse(null);
    }

    public ConsultantResponseDto findConsultantDtoById(UUID id) {
        Consultant consultant = findConsultantById(id);
        assert consultant != null;
        List<RegisteredTime> timeByConsultantId = registeredTimeService.getTimeByConsultantId(consultant.getId());
        /*List<RegisteredTime> timeByConsultantId = registeredTimeService.getOtherTimeByConsultantId(consultant.getId());*/
        List<RegisteredTimeResponseDto> consultantTimeDto = getConsultantTimeDto(timeByConsultantId);
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
                        el.getTotalHours()))
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
                    8));
        }
        return consultantTimeDtoList;
    }

    public Map<Integer, RegisteredTimeDto> getTimeRegisteredByConsultant(UUID id) {
        List<RegisteredTime> registeredTimeList = registeredTimeService.getTimeByConsultantId(id);
        Map<Integer, RegisteredTimeDto> mappedRecords = new HashMap<>();
        String prevType = registeredTimeList.get(0).getType();
        int generatedKey = 0;
        for (RegisteredTime registeredTime : registeredTimeList) {
            if (mappedRecords.containsKey(generatedKey)) {
                if (prevType.equals(registeredTime.getType())) {
                    mappedRecords.computeIfPresent(generatedKey, (s, registeredTimeDto) ->
                            new RegisteredTimeDto(registeredTimeDto.startDate(),
                                    registeredTime.getEndDate(),
                                    registeredTime.getType()
                            ));
                } else {
                    generatedKey++;
                    mappedRecords.put(generatedKey,
                            new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                                    registeredTime.getEndDate(),
                                    registeredTime.getType()));
                }
            } else {
                mappedRecords.put(generatedKey,
                        new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                                registeredTime.getEndDate(),
                                registeredTime.getType()));
            }
            prevType = registeredTime.getType();
        }
        return fillRegisteredTimeGaps(sortMap(mappedRecords));
//        return sortMap(mappedRecords);
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
                                    "PGP"));
                    j = daysBetween;
                }
            }
            System.out.println("resultSet = " + resultSet);
        }
        return resultSet;
    }
}
