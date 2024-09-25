package salt.consultanttracker.api.consultant.dto;

import salt.consultanttracker.api.consultant.Consultant;
import salt.consultanttracker.api.demo.DemoConsultant;
import salt.consultanttracker.api.meetings.dto.MeetingsDto;
import salt.consultanttracker.api.reddays.CountryCode;

import java.util.List;
import java.util.UUID;

import static salt.consultanttracker.api.utils.Country.SWEDEN;

public record SingleConsultantResponseListDto(UUID id,
                                              String fullName,
                                              String email,
                                              String responsiblePt,
                                              String client,
                                              String country,
                                              TotalDaysStatisticsDto totalDaysStatistics,
                                              List<ClientsListDto> clientsList,
                                              List<MeetingsDto> meetings) {

    public static SingleConsultantResponseListDto toDto(
            Consultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<ClientsListDto> clientsListDto,
            List<MeetingsDto> meetings) {
        String responsiblePt = "";

        if (consultant.getSaltUser().getFullName() != null) {
            responsiblePt = consultant.getSaltUser().getFullName();
        }

        return new SingleConsultantResponseListDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                responsiblePt,
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                clientsListDto,
                meetings
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
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                clientsListDto,
                meetings
        );
    }
}
