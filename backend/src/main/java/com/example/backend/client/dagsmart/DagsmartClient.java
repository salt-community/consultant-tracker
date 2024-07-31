package com.example.backend.client.dagsmart;

import com.example.backend.client.dagsmart.dto.RedDaysFromDagsmartDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Data
public class DagsmartClient {
    private final WebClient CLIENT_URL;

    public DagsmartClient(@Value("${DAGSMART.URL}") String baseUrl) {
        CLIENT_URL = WebClient.create(baseUrl);
    }

    public RedDaysFromDagsmartDto[] getRedDaysPerYear(int year) {
        RedDaysFromDagsmartDto[] dto = CLIENT_URL.get()
                .uri("holidays?year={year}", year)
                .retrieve()
                .bodyToMono(RedDaysFromDagsmartDto[].class)
                .block();
        assert dto != null;
        return dto;
    }
}
