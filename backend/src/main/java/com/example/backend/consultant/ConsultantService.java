package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperConsultancyTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
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

    public List<ConsultantResponseDto> getAllConsultants() {
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
        List<Consultant> consultantsList = consultantRepository.findAll();

        return consultantsList.stream().map(ConsultantResponseDto::toDto).toList();
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
        AtomicReference<Double> totalHoursResponse = new AtomicReference<>(0.0);
        consultancyTime.forEach(el -> totalHoursResponse.updateAndGet(v -> v + el.totalHours()));
        return totalHoursResponse.get();
    }
}