package salt.consultanttracker.api.consultant.dto;

import salt.consultanttracker.api.consultant.Consultant;
import salt.consultanttracker.api.demo.DemoConsultant;
import salt.consultanttracker.api.registeredtime.dto.RegisteredTimeResponseDto;
import salt.consultanttracker.api.timechunks.TimeChunks;

import java.util.List;
import java.util.UUID;

import static salt.consultanttracker.api.utils.Country.*;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String responsiblePt,
                                    String client,
                                    String country,
                                    TotalDaysStatisticsDto totalDaysStatistics,
                                    List<RegisteredTimeResponseDto> registeredTimeDtoList) {

    public static ConsultantResponseDto toDto(
            Consultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<TimeChunks> timeChunks) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getResponsiblePT() != null ? consultant.getResponsiblePT().getFullName() : "",
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? SE.country : NO.country,
                totalDaysStatistics,
                timeChunks.stream().map(RegisteredTimeResponseDto::fromTimeChunks).toList()
                );
    }
    public static ConsultantResponseDto toDto(
            DemoConsultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<TimeChunks> timeChunks) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? SE.country : NO.country,
                totalDaysStatistics,
                timeChunks.stream().map(RegisteredTimeResponseDto::fromTimeChunks).toList()
        );
    }
}
