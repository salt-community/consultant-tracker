package salt.consultanttracker.api.client.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class NotionResponse {
    private List<String> consultantsList = new ArrayList<>();

    @JsonProperty("results")
    private void unpackNested(List<Map<String, Object>> results) {

        consultantsList = results.stream()
                .filter(p -> {
                    Map<String, Object> properties = (Map<String, Object>)p.get("properties");
                    Map<String, Object> names = (Map<String, Object>) properties.get("Name");
                    List<Map<String, Object>> title = (List<Map<String, Object>>) names.get("title");
                    return !title.isEmpty();
                })
                .map(p -> {
                    Map<String, Object> properties = (Map<String, Object>)p.get("properties");
                    Map<String, Object> names = (Map<String, Object>) properties.get("Name");
                    List<Map<String, Object>> title = (List<Map<String, Object>>) names.get("title");
                    return (String) title.getFirst().get("plain_text");
                })
                .toList();
    }
}
