package salt.consultanttracker.api.client.notion.dtos;

import salt.consultanttracker.api.responsiblept.ResponsiblePT;

import java.util.UUID;

import static salt.consultanttracker.api.responsiblept.Role.PT;

public record ResponsiblePTDto(String name, UUID id, String email) {
    public static ResponsiblePT toResponsiblePT(ResponsiblePTDto dto){
        return new ResponsiblePT(dto.id(), dto.name(), dto.email(), PT.role);
    }
}
