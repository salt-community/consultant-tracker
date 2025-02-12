package salt.consultanttracker.api.consultant.dto;

import salt.consultanttracker.api.consultant.Consultant;
import salt.consultanttracker.api.demo.DemoConsultant;
import salt.consultanttracker.api.meetings.dto.MeetingsDto;

import java.util.List;
import java.util.UUID;

import static salt.consultanttracker.api.utils.Country.*;

public record SingleConsultantResponseListDto(UUID id,
                                              String fullName,
                                              String email,
                                              String responsiblePt,
                                              String client,
                                              String country,
                                              TotalDaysStatisticsDto totalDaysStatistics,
                                              List<ClientsListDto> clientsList,
                                              List<MeetingsDto> meetings,
                                              String gitHubImgUrl) {

    public static SingleConsultantResponseListDto toDto(
            Consultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<ClientsListDto> clientsListDto,
            List<MeetingsDto> meetings) {
        String responsiblePt = "";

        if (consultant.getResponsiblePT()!=null && consultant.getResponsiblePT().getFullName() != null) {
            responsiblePt = consultant.getResponsiblePT().getFullName();
        }

        return new SingleConsultantResponseListDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                responsiblePt,
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? SE.country : NO.country,
                totalDaysStatistics,
                clientsListDto,
                meetings,
                consultant.getGithubImageUrl()
        );
    }

    public static SingleConsultantResponseListDto toDto(
            DemoConsultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<ClientsListDto> clientsListDto,
            List<MeetingsDto> meetings) {

        return new SingleConsultantResponseListDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? SE.country : NO.country,
                totalDaysStatistics,
                clientsListDto,
                meetings,
                null

        );
    }
}
