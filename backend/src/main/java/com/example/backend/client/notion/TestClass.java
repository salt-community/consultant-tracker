package com.example.backend.client.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TestClass {
    private List<String> consultantsLit = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("results")
    private void unpackNested(List<Map<String, Object>> results) {
        /*
        *   results <String, Object> ->
        *   properties <String, Object> ->
            Names <String, Object> ->
            title <String, Object> ->
            plain_text <String, String>
     */
        consultantsLit = results.stream()
                .map(p -> {
                    Map<String, Object> properties = (Map<String, Object>)p.get("properties");
                    Map<String, Object> names = (Map<String, Object>) properties.get("Name");
                    List<Map<String, Object>> title = (List<Map<String, Object>>) names.get("title");
                    return (String) title.getFirst().get("plain_text");
                })
                .toList();

//        Map<String,String> objectMap = (Map<String,String>)results.get("object");
//        this.object = objectMap.get("object");
    }
}
