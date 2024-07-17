package com.example.backend.client;


import com.example.backend.client.dto.TimekeeperUserListResponseDto;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
                            tkUser.id());
                }).toList();
    }
}