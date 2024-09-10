package com.example.backend.timeChunks;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.redDay.RedDayService;
import com.example.backend.registeredTime.RegisteredTime;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.example.backend.client.timekeeper.Activity.CONSULTANCY_TIME;
import static com.example.backend.client.timekeeper.Activity.REMAINING_DAYS;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Data
public class TimeChunksService {

    private final TimeChunksRepository timeChunksRepository;
    private final RegisteredTimeService registeredTimeService;
    private final RedDayService redDayService;

    public void saveTimeChunksForAllConsultants(List<Consultant> consultants) {
        Logger logger = Logger.getLogger(TimeChunksService.class.getName());
        logger.info("inside saveTimeChunksForAllConsultants");
        for (Consultant consultant : consultants) {
            logger.info("for loop consultant: " + consultant.getFullName());
            List<TimeChunks> timeChunksToSave = getGroupedConsultantsRegisteredTimeItems(consultant.getId());
            if (timeChunksToSave == null) {
                continue;
            }
            List<TimeChunks> timeChunksRemainingDaysToSave = timeChunksToSave.stream().filter(el -> el.getType().equals(REMAINING_DAYS.activity)).toList();
            if(!timeChunksRemainingDaysToSave.isEmpty()){
                List<TimeChunks> chunksInDB = timeChunksRepository.findAllById_ConsultantIdAndType(consultant.getId(), REMAINING_DAYS.activity);
                if (!chunksInDB.isEmpty()
                        && (chunksInDB.getFirst().getId().getStartDate().isAfter(timeChunksRemainingDaysToSave.getFirst().getId().getStartDate())
                        || chunksInDB.getFirst().getId().getStartDate().isBefore(timeChunksRemainingDaysToSave.getFirst().getId().getStartDate()))) {
                    timeChunksRepository.deleteById(chunksInDB.getFirst().getId());
                }
            }
            timeChunksRepository.saveAll(timeChunksToSave);
        }
        logger.info("Done with saveTimeChunksForAllConsultants");
    }

    public List<TimeChunks> getGroupedConsultantsRegisteredTimeItems(UUID id) {
        List<RegisteredTime> registeredTimeList = registeredTimeService.getTimeByConsultantId(id);
        if (registeredTimeList.isEmpty()) {
            return null;
        }
        Map<Integer, RegisteredTimeDto> groupedRegisteredTime = groupRegisteredTimeItemsIntoMap(registeredTimeList, id);
        Map<Integer, RegisteredTimeDto> groupedRegisteredTimeWithFilledGaps = fillRegisteredTimeGaps(sortMap(groupedRegisteredTime), id);
        List<RegisteredTimeDto> regTimeRespDto = new ArrayList<>(groupedRegisteredTimeWithFilledGaps.values());
        List<TimeChunks> timeChunks = new ArrayList<>(regTimeRespDto
                .stream()
                .map(t -> new TimeChunks(new TimeChunksKey(id, t.startDate()), t.type(), t.endDate(), t.days(), t.projectName()))
                .toList());

        TimeChunks remainingConsultancyTimeByConsultantId = registeredTimeService.getRemainingConsultancyTimeByConsultantId(id);
        if (remainingConsultancyTimeByConsultantId != null) {
            timeChunks.add(remainingConsultancyTimeByConsultantId);
        }
        return timeChunks;
    }

    private Map<Integer, RegisteredTimeDto> groupRegisteredTimeItemsIntoMap(List<RegisteredTime> registeredTimeList, UUID id) {
        Map<Integer, RegisteredTimeDto> mappedRecords = new HashMap<>();
        String prevType = registeredTimeList.getFirst().getType();
        int generatedKey = 0;
        for (RegisteredTime registeredTime : registeredTimeList) {
            if (mappedRecords.containsKey(generatedKey)) {
                LocalDate startDateBefore = mappedRecords.get(generatedKey).endDate().toLocalDate();
                long daysBetween = DAYS.between(startDateBefore, registeredTime.getId().getStartDate().toLocalDate());
                boolean onlyRedDayaAndWeekendsBetween = redDayService.checkRedDaysOrWeekend(daysBetween, startDateBefore, id, "single check") == (daysBetween - 1);
                if (prevType.equals(registeredTime.getType()) && onlyRedDayaAndWeekendsBetween) {
                    computeMapEntry(mappedRecords, registeredTime, generatedKey);
                } else {
                    generatedKey++;
                    createNewMapEntry(mappedRecords, registeredTime, generatedKey);
                }
            } else {
                createNewMapEntry(mappedRecords, registeredTime, generatedKey);
            }
            prevType = registeredTime.getType();
        }
        return mappedRecords;
    }

    private Map<Integer, RegisteredTimeDto> fillRegisteredTimeGaps(Map<Integer, RegisteredTimeDto> resultSet, UUID consultantId) {
        Map<Integer, RegisteredTimeDto> filledGapsMap = new HashMap<>(resultSet);
        for (int i = 0; i < resultSet.size() - 1; i++) {
            LocalDate dateBefore = resultSet.get(i).endDate().toLocalDate();
            LocalDate dateAfter = resultSet.get(i + 1).startDate().toLocalDate();
            long daysBetween = DAYS.between(dateBefore, dateAfter);
            if (daysBetween > 1) {
                int nonWorkingDays = redDayService.checkRedDaysOrWeekend(daysBetween, dateBefore, consultantId, "single check");
                fillGapsWithNoRegisteredTime(nonWorkingDays, daysBetween, filledGapsMap, dateBefore, dateAfter, i, consultantId);
            }
        }
        return filledGapsMap;
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

    private void computeMapEntry(Map<Integer, RegisteredTimeDto> mappedRecords, RegisteredTime registeredTime, int generatedKey) {
        mappedRecords.computeIfPresent(generatedKey, (key, registeredTimeDto) ->
                new RegisteredTimeDto(registeredTimeDto.startDate(),
                        registeredTime.getEndDate(),
                        registeredTimeDto.type(),
                        registeredTime.getProjectName(),
                        registeredTimeDto.days() + 1
                ));
    }

    private void createNewMapEntry(Map<Integer, RegisteredTimeDto> mappedRecords, RegisteredTime registeredTime, int generatedKey) {
        mappedRecords.put(generatedKey,
                new RegisteredTimeDto(registeredTime.getId().getStartDate(),
                        registeredTime.getEndDate(),
                        registeredTime.getType(),
                        registeredTime.getProjectName(),
                        1));
    }

    private void fillGapsWithNoRegisteredTime(int nonWorkingDays, Long daysBetween, Map<Integer, RegisteredTimeDto> filledGapsMap, LocalDate dateBefore, LocalDate dateAfter, int i, UUID id) {
        if (nonWorkingDays != 0) {
            filledGapsMap.computeIfPresent(i, (key, value) ->
                    new RegisteredTimeDto(value.startDate(),
                            value.endDate().plusDays(nonWorkingDays),
                            value.type(),
                            value.projectName(),
                            value.days()));
        }
        if (nonWorkingDays != daysBetween - 1) {
            int totalNonWorkingDays = redDayService.checkRedDaysOrWeekend(daysBetween, dateBefore, id, "multiple check");
            filledGapsMap.put(filledGapsMap.size(),
                    new RegisteredTimeDto(dateBefore.plusDays(nonWorkingDays + 1).atStartOfDay(),
                            dateAfter.minusDays(1).atTime(23, 59, 59),
                            "No Registered Time",
                            "No Registered Time",
                            (int) (daysBetween - 1 - totalNonWorkingDays)));
        }
    }

    public List<TimeChunks> getTimeChunksByConsultant(UUID id) {
        return timeChunksRepository.findAllById_ConsultantIdOrderById_StartDateAsc(id);
    }

    public List<TimeChunks> getTimeChunksByConsultantIdAndClient(UUID id, List<String> client) {
        return timeChunksRepository.findAllById_ConsultantIdAndProjectNameOrderById_StartDateAsc(id, client);
    }
}
