package com.example.backend.client;


import com.example.backend.client.dto.TimekeeperRegisteredTimeListResponseDto;
import com.example.backend.client.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserListResponseDto;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.example.backend.client.Activity.CONSULTANCY_TIME;

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

    public TimekeeperUserResponseDto getUser(Long id) {
        TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                .uri("api/v1/user/{id}", id)
                .header("Authorization", HEADER)
                .retrieve()
                .bodyToMono(TimekeeperUserListResponseDto.class)
                .block();
        assert dto != null;
        return dto.timekeeperUsers().stream()
                .map(tkUser -> {
                    return new TimekeeperUserResponseDto(
                            tkUser.firstName(),
                            tkUser.lastName(),
                            tkUser.email(),
                            tkUser.phone(),
                            tkUser.tags(),
                            tkUser.id());
                }).toList().getFirst();
    }

    public List<TimekeeperUserResponseDto> getUsers() {
        TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                .uri("api/v1/user")
                .header("Authorization", HEADER)
                .retrieve()
                .bodyToMono(TimekeeperUserListResponseDto.class)
                .block();
        assert dto != null;
        return dto.timekeeperUsers().stream()
                .map(tkUser -> {
                    return new TimekeeperUserResponseDto(
                            tkUser.firstName(),
                            tkUser.lastName(),
                            tkUser.email(),
                            tkUser.phone(),
                            tkUser.tags(),
                            tkUser.id());
                }).toList();
    }

    public List<TimekeeperRegisteredTimeResponseDto> getTimeRegisteredByConsultant(Long id) {
        TimekeeperRegisteredTimeListResponseDto dto = CLIENT_URL.get()
                .uri("api/v1/TimeRegistration?UserId={id}&PageSize=200", id)
                .header("Authorization", HEADER)
                .retrieve()
                .bodyToMono(TimekeeperRegisteredTimeListResponseDto.class)
                .block();
        assert dto != null;
        return dto.consultancyTime().stream()
                .map(time -> {
                    return new TimekeeperRegisteredTimeResponseDto(
                            time.totalHours(),
                            time.activityName(),
                            time.startTime());
                }).toList();
    }
}