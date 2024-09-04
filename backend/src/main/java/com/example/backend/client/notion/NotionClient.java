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
                        "contains": "7f26998b-5296-439a-90ef-07dad83d3de2"
                         }
                     }
                    }""";

        JsonNode dto = CLIENT_URL.post()
                .uri("/{dbId}/query", DB_ID)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        System.out.println("dto = " + dto);
    }
}
