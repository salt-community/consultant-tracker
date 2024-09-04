package com.example.backend.client.notion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

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
            "filter": {
              "property": "Responsible",
              "people": {
                "contains": "7f26998b-5296-439a-90ef-07dad83d3de2"
             }
        }""";

        JSONObject jsonObj = new JSONObject(requestBody);

        Map<String, String> contains = new HashMap<>();
        contains.put("contains", "7f26998b-5296-439a-90ef-07dad83d3de2");
        Map<String, String> people = new HashMap<>();
        people.put("people", contains.toString());
        Map<String, String> property = new HashMap<>();
        property.put("property", "Responsible");
        MultiValueMap<String, String> filter = new LinkedMultiValueMap<>();
        filter.put("filter", List.of(property.toString(), people.toString()));

        System.out.println("filter = " + filter);

        JsonNode dto = CLIENT_URL.post()
                .uri("/{dbId}/query", DB_ID)
                .body(jsonObj.toString(), String.class)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        System.out.println("dto = " + dto);
    }
}
