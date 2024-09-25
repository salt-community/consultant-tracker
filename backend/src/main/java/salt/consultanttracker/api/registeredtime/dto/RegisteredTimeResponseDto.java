package salt.consultanttracker.api.registeredtime.dto;

import salt.consultanttracker.api.timechunks.TimeChunks;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisteredTimeResponseDto(UUID registeredTimeId,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate,
                                        String type,
                                        String projectName,
                                        int days
) {
    public static RegisteredTimeResponseDto fromRegisteredTimeDto(RegisteredTimeDto registeredTimeDto) {
        return new RegisteredTimeResponseDto(UUID.randomUUID(),
                registeredTimeDto.startDate(),
                registeredTimeDto.endDate(),
                registeredTimeDto.type(),
                registeredTimeDto.projectName(),
                registeredTimeDto.days());
    }
    public static RegisteredTimeResponseDto fromTimeChunks(TimeChunks timeChunks) {
        return new RegisteredTimeResponseDto(UUID.randomUUID(),
                timeChunks.getId().getStartDate(),
                timeChunks.getEndDate(),
                timeChunks.getType(),
                timeChunks.getProjectName(),
                timeChunks.getTotalDays());
    }
}
