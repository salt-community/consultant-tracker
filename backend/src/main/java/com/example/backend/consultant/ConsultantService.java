package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.exceptions.ConsultantNotFoundException;
import com.example.backend.registeredTime.RegisteredTime;
import com.example.backend.registeredTime.RegisteredTimeKey;
import com.example.backend.registeredTime.RegisteredTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;

    public List<ConsultantResponseDto> getAllConsultantDtos() {
        List<Consultant> consultantsList = getAllConsultants();
        return consultantsList.stream().map(ConsultantResponseDto::toDto).toList();
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //    @Scheduled(cron = "0 0 0 * * *")
    public void fetchDataFromTimekeeper() {
        List<TimekeeperUserResponseDto> timekeeperUserResponseDto = timekeeperClient.getUsers();
        assert timekeeperUserResponseDto != null;
        List<Long> idsToAdd = checkTimekeeperUsersWithDatabase(timekeeperUserResponseDto);
        if (!idsToAdd.isEmpty()) {
            idsToAdd.forEach(id -> {
                TimekeeperUserResponseDto tkUser = timekeeperUserResponseDto.stream()
//                        .filter(el-> !el.tags().stream().filter(element -> element.getName().equals("På uppdrag")).toList().isEmpty())
                        .filter(u -> Objects.equals(u.id(), id)).findFirst().orElse(null);
                if (tkUser != null) {
                    Consultant consultant = new Consultant(
                            UUID.randomUUID(),
                            tkUser.firstName().concat(tkUser.lastName()),
                            tkUser.email(),
                            tkUser.phone(),
                            id);
                    createConsultant(consultant);
                }
            });
        }
        fetchRecordedTimeForConsultant();
    }

    private void fetchRecordedTimeForConsultant() {
        List<Consultant> consultants = getAllConsultants();
        // for each consultant get start date, absences, calculate remaining time
        for (Consultant consultant : consultants) {
            List<ConsultantTimeDto> consultantRegisteredTime = getConsultantTimeDto(consultant.getId(), consultant.getTimekeeperId());
            registeredTimeService.saveConsultantTime(consultantRegisteredTime);
        }
    }

    private List<Long> checkTimekeeperUsersWithDatabase(List<TimekeeperUserResponseDto> timekeeperUserResponseDto) {
        List<Long> idsToAdd = new ArrayList<>();
        timekeeperUserResponseDto.forEach(tkUser -> {
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                System.out.println("No consultant found with id " + tkUser.id());
                idsToAdd.add(tkUser.id());
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
        return ConsultantResponseDto.toDto(consultant);
    }

    public Double getConsultancyHoursByUserId(UUID id) {
        Consultant consultant = consultantRepository.findById(id).orElseThrow(() -> new ConsultantNotFoundException("Consultant not found"));
        List<TimekeeperRegisteredTimeResponseDto> consultancyTime = timekeeperClient.getTimeRegisteredByConsultant(consultant.getTimekeeperId());
        System.out.println("consultancyTime = " + consultancyTime);
        AtomicReference<Double> totalHoursResponse = new AtomicReference<>(0.0);
        consultancyTime.forEach(el -> totalHoursResponse.updateAndGet(v -> v + el.totalHours()));
        return totalHoursResponse.get();
    }

    public List<ConsultantTimeDto> getAllConsultantsTimeItems() {
        fetchDataFromTimekeeper();
        // get consultants from timekeeper/db
        List<Consultant> consultants = getAllConsultants();
        // for each consultant get start date, absences, calculate remaining time
        List<RegisteredTime> consultantTimeDtoList = new ArrayList<>();
        for (Consultant consultant : consultants) {
//            List<ConsultantTimeDto> consultantRegisteredTime = getConsultantTimeDto(consultant.getId(), consultant.getTimekeeperId());
//            consultantTimeDtoList.addAll(consultantRegisteredTime);
            List<RegisteredTime> timeByConsultantId = registeredTimeService.getTimeByConsultantId(consultant.getId());
            consultantTimeDtoList.addAll(timeByConsultantId);
        }
        return consultantTimeDtoList
                .stream()
                .map(el -> new ConsultantTimeDto(
                        el.getId(),
//                        el.getConsultant().getId(),
//                        el.getStartDate(),
                        el.getEndDate(),
                        el.getType(),
                        el.getTotalDays()))
                .toList();
    }


    private List<ConsultantTimeDto> getConsultantTimeDto(UUID consultantId, Long timekeeperId) {
        List<TimekeeperRegisteredTimeResponseDto> consultancyTime = timekeeperClient.getTimeRegisteredByConsultant(timekeeperId);
        List<ConsultantTimeDto> consultantTimeDtoList = new ArrayList<>();
        for (TimekeeperRegisteredTimeResponseDto item : consultancyTime) {
            consultantTimeDtoList.add(new ConsultantTimeDto(
                    new RegisteredTimeKey(consultantId,item.startTime().withHour(0).withMinute(0).withSecond(0)),
                    item.startTime().withHour(23).withMinute(59).withSecond(59),
                    item.activityName(),
                    1));
        }
        return consultantTimeDtoList;
    }
}