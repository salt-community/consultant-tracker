package com.example.backend.client;


import com.example.backend.client.dto.TimekeeperRegisteredTimeListResponseDto;
import com.example.backend.client.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserListResponseDto;
import com.example.backend.client.dto.TimekeeperUserDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class TimekeeperClient {
    private final WebClient CLIENT_URL;
    private final String HEADER;

    public TimekeeperClient(@Value("${TIMEKEEPER.URL}") String baseUrl,
                            @Value("${TIMEKEEPER.AUTH}") String HEADER) {
        CLIENT_URL = WebClient.create(baseUrl);
        this.HEADER = HEADER;
    }

    public TimekeeperUserDto getUser(Long id) {
        TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                .uri("api/v1/user/{id}", id)
                .header("Authorization", HEADER)
                .retrieve()
                .bodyToMono(TimekeeperUserListResponseDto.class)
                .block();
        assert dto != null;
        return dto.timekeeperUsers().stream()
                .map(tkUser -> {
                    return new TimekeeperUserDto(
                            tkUser.firstName(),
                            tkUser.lastName(),
                            tkUser.email(),
                            tkUser.phone(),
                            tkUser.tags(),
                            tkUser.id(),
                            tkUser.isActive(),
                            tkUser.isEmployee());
                }).toList().getFirst();
    }

    public List<TimekeeperUserDto> getUsers() {
        int index = 0;
        int numOfPages = 1;
        List<TimekeeperUserDto> users = new ArrayList<>();
        while (index < numOfPages) {
            TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                    .uri("api/v1/user?PageIndex={index}&TagIDs=8571", index)
                    .header("Authorization", HEADER)
                    .retrieve()
                    .bodyToMono(TimekeeperUserListResponseDto.class)
                    .block();
            assert dto != null;
            users.addAll(dto.timekeeperUsers().stream()
                    .map(tkUser -> {
                        return new TimekeeperUserDto(
                                tkUser.firstName(),
                                tkUser.lastName(),
                                tkUser.email(),
                                tkUser.phone(),
                                tkUser.tags(),
                                tkUser.id(),
                                tkUser.isActive(),
                                tkUser.isEmployee());
                    }).toList());
            if (index == 0) {
                numOfPages = dto.totalPages();
            }
            index++;
        }
        return users;
    }

    public List<TimekeeperRegisteredTimeResponseDto> getTimeRegisteredByConsultant(Long id) {
        int index = 0;
        int numOfPages = 1;
        List<TimekeeperRegisteredTimeResponseDto> registeredTime = new ArrayList<>();
        while (index < numOfPages) {
            TimekeeperRegisteredTimeListResponseDto dto = CLIENT_URL.get()
                    .uri("api/v1/TimeRegistration?UserId={id}&PageIndex={index}", id, index)
                    .header("Authorization", HEADER)
                    .retrieve()
                    .bodyToMono(TimekeeperRegisteredTimeListResponseDto.class)
                    .block();

            assert dto != null;
            registeredTime.addAll(dto.consultancyTime().stream()
                    .map(time -> {
                        return new TimekeeperRegisteredTimeResponseDto(
                                time.totalHours(),
                                time.activityName(),
                                time.date());
                    }).toList());

            if (index == 0) {
                numOfPages = dto.totalPages();
            }
            index++;
        }
        return registeredTime;
    }
}