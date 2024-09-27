package salt.consultanttracker.api.client.notion.dtos;

import java.util.UUID;

public record ResponsiblePTDto(String name, UUID id, String email) {
}
