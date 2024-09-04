package com.example.backend.client.notion;

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
                }).exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build()).build();
        this.DB_ID = DB_ID;
    }

    public void getUsersFromNotion() {

        TestClass dto = CLIENT_URL.post()
                .uri("/{dbId}/query", DB_ID)
                .retrieve()
                .bodyToMono(TestClass.class)
                .block();
        System.out.println("dto = " + dto);
    }
}
