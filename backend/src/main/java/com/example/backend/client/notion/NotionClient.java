package com.example.backend.client.notion;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Data
public class NotionClient {
    private final WebClient CLIENT_URL;
    private final String HEADER_AUTH;
    private final String DB_ID;

    public NotionClient(@Value("${NOTION.URL}") String baseUrl,
                        @Value("${NOTION.AUTH}") String HEADER_AUTH,
                        @Value("${NOTION.DB_ID}") String DB_ID) {
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
    }

    public void getUsersFromNotion() {

        String requestBody = """
                    {
                    "filter": {
                      "property": "Responsible",
                      "people": {
                        "contains": "06e54dd7-ef44-49ee-b3ef-032ccef2b458"
                         }
                     }
                    }""";

        NotionResponse dto = CLIENT_URL.post()
                .uri("/{dbId}/query", DB_ID)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(NotionResponse.class)
                .block();
        System.out.println("dto = " + dto);
        assert dto != null;
        System.out.println("dto.size() = " + dto.getConsultantsList().size());
    }
}
