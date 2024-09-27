package salt.consultanttracker.api.client.notion.dtos;

import java.util.List;
import java.util.UUID;

public record ConsultantsNProxyDto(String name, UUID id, List<ResponsiblePTDto> listOfResponsiblePTs) {
}
