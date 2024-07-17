package com.example.backend.client;


import com.example.backend.client.dto.TimekeeperUserListResponseDto;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TimekeeperClient {
    private final WebClient CLIENT_URL;
    private String HEADER;

    @Value("${TIMEKEEPER.AUTH}")
    private static String HEADER_VALUE;
    @Value("${TIMEKEEPER.URL}")
    private static String BASE_URL;


//    public TimekeeperClient(@Value("${TIMEKEEPER.URL}") String baseUrl,
//                            @Value("${TIMEKEEPER.AUTH}")
//                            String HEADER) {
//        CLIENT_URL = WebClient.create(baseUrl);
//        this.HEADER = HEADER;
//    }

    public TimekeeperClient() {
        CLIENT_URL = WebClient.create(BASE_URL);
        this.HEADER = HEADER_VALUE;
    }

    public TimekeeperUserResponseDto getUser(Long id) {
        System.out.println("HEADER: " + HEADER);
        TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                .uri("api/v1/user/{id}", id)
                .header("Authorization", "Basic amFnb2RhLmJvZG5hckBhcHBsaWVkdGVjaG5vbG9neS5zZTp2bzN0c2JwNQ==")
                .retrieve()
                .bodyToMono(TimekeeperUserListResponseDto.class)
                .block();
        assert dto != null;
        return dto.timekeeperUsers().stream()
                .map(tkUser -> {
                    var user = new TimekeeperUserResponseDto(
                            tkUser.firstName(),
                            tkUser.lastName(),
                            tkUser.email(),
                            tkUser.phone(),
                            tkUser.id());
                    return user;
                }).toList().get(0);
    }
}