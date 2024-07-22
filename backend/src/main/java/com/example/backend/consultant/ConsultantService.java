package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperConsultancyTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.consultant.dto.ConsultantTimeResponseDto;
import com.example.backend.exceptions.ConsultantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;

    public List<ConsultantResponseDto> getAllConsultantDtos() {
        List<Consultant> consultantsList = getAllConsultants();
        return consultantsList.stream().map(ConsultantResponseDto::toDto).toList();
    }

    public List<Consultant> getAllConsultants() {
        List<TimekeeperUserResponseDto> timekeeperUserResponseDto = timekeeperClient.getUsers();
        assert timekeeperUserResponseDto != null;
        List<Long> idsToAdd = checkTimekeeperUsersWithDatabase(timekeeperUserResponseDto);
        if (!idsToAdd.isEmpty()) {
            idsToAdd.forEach(id -> {
                TimekeeperUserResponseDto tkUser = timekeeperUserResponseDto.stream()
                        .filter(u -> Objects.equals(u.id(), id)).findFirst().orElse(null);
                Consultant consultant = new Consultant(
                        UUID.randomUUID(),
                        tkUser.firstName().concat(tkUser.lastName()),
                        tkUser.email(),
                        tkUser.phone(),
                        id);
                createConsultant(consultant);
            });
        }
        return consultantRepository.findAll();
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

    public ConsultantResponseDto findConsultantById(UUID id) {
        Consultant consultant = consultantRepository.findById(id).orElse(null);
        assert consultant != null;
        return ConsultantResponseDto.toDto(consultant);
    }

    public Double getConsultancyHoursByUserId(UUID id) {
        Consultant consultant = consultantRepository.findById(id).orElseThrow(() -> new ConsultantNotFoundException("Consultant not found"));
        List<TimekeeperConsultancyTimeResponseDto> consultancyTime = timekeeperClient.getConsultancyTime(consultant.getTimekeeperId());
        System.out.println("consultancyTime = " + consultancyTime);
        AtomicReference<Double> totalHoursResponse = new AtomicReference<>(0.0);
        consultancyTime.forEach(el -> totalHoursResponse.updateAndGet(v -> v + el.totalHours()));
        return totalHoursResponse.get();
    }

    public List<ConsultantTimeDto> getAllConsultantsTimeItems() {
        // get consultants from timekeeper/db
        List<Consultant> consultants = getAllConsultants();
        // for each consultant get start date, absences, calculate remaining time
        List<ConsultantTimeDto> consultantTimeDtoList = new ArrayList<>();
        for (Consultant consultant : consultants) {
            List<ConsultantTimeDto> tempName = getConsultantTimeDto(consultant.getId(), consultant.getTimekeeperId());
            consultantTimeDtoList.addAll(tempName);
        }
        return consultantTimeDtoList;
    }

    private List<ConsultantTimeDto> getConsultantTimeDto(UUID consultantId, Long timekeeperId) {
        List<TimekeeperConsultancyTimeResponseDto> consultancyTime = timekeeperClient.getConsultancyTime(timekeeperId);
        List<ConsultantTimeDto> consultantTimeDtoList = new ArrayList<>();
        for (TimekeeperConsultancyTimeResponseDto item : consultancyTime) {
            consultantTimeDtoList.add(new ConsultantTimeDto(
                    UUID.randomUUID(),
                    consultantId,
                    item.startTime().withHour(0).withMinute(0).withSecond(0),
                    item.startTime().withHour(23).withMinute(59).withSecond(59),
                    item.activityName(),
                    1));
        }
        return consultantTimeDtoList;
    }
}