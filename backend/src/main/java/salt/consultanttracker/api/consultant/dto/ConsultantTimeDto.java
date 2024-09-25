package salt.consultanttracker.api.consultant.dto;

import salt.consultanttracker.api.registeredtime.RegisteredTime;
import salt.consultanttracker.api.registeredtime.RegisteredTimeKey;

import java.time.LocalDateTime;

public record ConsultantTimeDto(RegisteredTimeKey itemId,
                                LocalDateTime endDate,
                                String dayType,
                                double totalHours,
                                String projectName) {

    public static ConsultantTimeDto toConsultantTimeDto(RegisteredTime registeredTime){
        return new ConsultantTimeDto(
                registeredTime.getId(),
                registeredTime.getEndDate(),
                registeredTime.getType(),
                registeredTime.getTotalHours(),
                registeredTime.getProjectName()
                );
    }
}
