package salt.consultanttracker.api.client.timekeeper.dto;

import salt.consultanttracker.api.tag.Tag;

import java.util.List;

public record TimekeeperUserDto(String firstName,
                                String lastName,
                                String email,
                                String phone,
                                List<Tag> tags,
                                Long id,
                                boolean isActive,
                                String client,
                                String responsiblePT,
                                boolean isEmployee) {
}
