package salt.consultanttracker.api.client.timekeeper.dto;

import java.time.LocalDateTime;

public record TimekeeperRegisteredTimeResponseDto(
        Float totalHours,
        String activityName,
        LocalDateTime date,
        String projectName) {
}
