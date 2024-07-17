package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


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
}
