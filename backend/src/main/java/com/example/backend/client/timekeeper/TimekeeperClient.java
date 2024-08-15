package com.example.backend.client.timekeeper;

import com.example.backend.client.timekeeper.dto.TimekeeperRegisteredTimeListResponseDto;
import com.example.backend.client.timekeeper.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.client.timekeeper.dto.TimekeeperUserListResponseDto;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class TimekeeperClient {
    private final WebClient CLIENT_URL;
    private final String HEADER;

    public TimekeeperClient(@Value("${TIMEKEEPER.URL}") String baseUrl,
                            @Value("${TIMEKEEPER.AUTH}") String HEADER) {
//        CLIENT_URL = WebClient.create(baseUrl);
        CLIENT_URL = WebClient.builder().baseUrl(baseUrl).exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
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
                            null,
                            null,
                            tkUser.isEmployee());
                }).toList().getFirst();
    }

    public List<TimekeeperUserDto> getUsers() {
        int index = 0;
        int numOfPages = 1;
        List<TimekeeperUserDto> users = new ArrayList<>();
        while (index < numOfPages) {
            TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                    .uri("api/v1/user?PageIndex={index}&pageSize=100&TagIDs=8561&TagIDs=8571", index)
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
                                tkUser.client(),
                                tkUser.responsiblePT(),
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
                    .uri("api/v1/TimeRegistration?UserId={id}&PageIndex={index}&pageSize=1000", id, index)
                    .header("Authorization", HEADER)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals,
                            response -> Mono.empty())
                    .bodyToMono(TimekeeperRegisteredTimeListResponseDto.class)
                    .block();
            assert dto != null;
            if (dto.consultancyTime() == null) {
                break;
            }
            registeredTime.addAll(dto.consultancyTime().stream()
                    .map(time -> {
                        return new TimekeeperRegisteredTimeResponseDto(
                                time.totalHours(),
                                time.activityName(),
                                time.date(),
                                time.projectName());
                    }).toList());

            if (index == 0) {
                numOfPages = dto.totalPages();
            }
            index++;
        }
        return registeredTime;
    }
}