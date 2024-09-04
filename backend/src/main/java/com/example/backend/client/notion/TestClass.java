package com.example.backend.client.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TestClass {
//    private Map<String, Object> results = new HashMap<>();
    private String object;
//    private Map<String, Object> properties;

    @SuppressWarnings("unchecked")
    @JsonProperty("results")
    private void unpackNested(List<Map<String,Object>> results) {
        this.object = (String) results.get(0).get("object");
//        Map<String,String> objectMap = (Map<String,String>)results.get("object");
//        this.object = objectMap.get("object");
    }
}
