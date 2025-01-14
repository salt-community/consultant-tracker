package salt.consultanttracker.api.client.notion.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record ConsultantsNProxyDto(String name, UUID id,
                                   @JsonProperty("responsiblePersonList") List<ResponsiblePTDto> listOfResponsiblePTs,
@JsonProperty("githubImageUrl") String githubImageUrl) {
}
