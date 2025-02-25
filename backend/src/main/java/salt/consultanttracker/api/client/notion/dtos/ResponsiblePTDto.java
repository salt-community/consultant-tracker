package salt.consultanttracker.api.client.notion.dtos;

import salt.consultanttracker.api.responsiblept.ResponsiblePT;

import java.util.UUID;

import static salt.consultanttracker.api.responsiblept.Role.PT;

public record ResponsiblePTDto(String name, UUID staffId, String email) {
    public static ResponsiblePT toResponsiblePT(ResponsiblePTDto dto){
        return new ResponsiblePT(dto.staffId(), dto.name(), dto.email(), PT.role);
    }

    @Override
    public String toString() {
        return "ResponsiblePTDto{" +
                "name='" + name + '\'' +
                ", id=" + staffId +
                ", email='" + email + '\'' +
                '}';
    }
}