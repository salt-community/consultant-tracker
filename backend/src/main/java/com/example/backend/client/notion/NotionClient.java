package com.example.backend.client.notion;

import com.example.backend.consultant.ConsultantService;
import com.example.backend.saltuser.SaltUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.logging.Logger;


@Service
@Data
public class NotionClient {
    private final WebClient CLIENT_URL;
    private final String HEADER_AUTH;
    private final String DB_ID;
    private final SaltUserService saltUserService;
    private final ConsultantService consultantService;
    private static final Logger LOGGER = Logger.getLogger(NotionClient.class.getName());

    public NotionClient(@Value("${NOTION.URL}") String baseUrl,
                        @Value("${NOTION.AUTH}") String HEADER_AUTH,
                        @Value("${NOTION.DB_ID}") String DB_ID,
                        SaltUserService saltUserService,
                        ConsultantService consultantService) {
        this.HEADER_AUTH = HEADER_AUTH;
        CLIENT_URL = WebClient.builder().baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Authorization", HEADER_AUTH);
                    httpHeaders.set("Notion-Version", "2022-06-28");
                    httpHeaders.set("Content-Type", "application/json");
                }).exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build()).build();
        this.DB_ID = DB_ID;
        this.saltUserService = saltUserService;
        this.consultantService = consultantService;
    }

    @Scheduled(cron="0 0 2 * * 4", zone = "Europe/Stockholm")
    public void getUsersFromNotion() {
        LOGGER.info("Fetching users from Notion");
        List<UUID> ptsIds = saltUserService.getAllPtsIds();

        String requestBody = """
                {
                "filter": {
                  "property": "Responsible",
                  "people": {
                    "contains": "%s"
                     }
                 }
                }""";

        Map<UUID, List<String>> consultantsAndPts = new HashMap<>();

// TODO fetch all pages

        for (UUID id : ptsIds) {
            String stringFormat = String.format(requestBody, id);
            NotionResponse dto = CLIENT_URL.post()
                    .uri("/{dbId}/query", DB_ID)
                    .bodyValue(stringFormat)
                    .retrieve()
                    .bodyToMono(NotionResponse.class)
                    .block();
            if (dto != null) {
                consultantsAndPts.put(id, dto.getConsultantsList());
            }
        }
        if (!consultantsAndPts.isEmpty()) {
            consultantService.updatePtsForConsultants(consultantsAndPts);
        }
        LOGGER.info("Users from Notion fetched");
    }
}
