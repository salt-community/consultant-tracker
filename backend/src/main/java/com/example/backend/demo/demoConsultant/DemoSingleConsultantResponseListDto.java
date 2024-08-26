package com.example.backend.demo.demoConsultant;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.dto.ClientsList;
import com.example.backend.consultant.dto.SingleConsultantResponseListDto;
import com.example.backend.consultant.dto.TotalDaysStatisticsDto;
import com.example.backend.redDay.CountryCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.example.backend.utils.Country.SWEDEN;

public record DemoSingleConsultantResponseListDto(UUID id,
                                                  String fullName,
                                                  String email,
                                                  String phoneNumber,
                                                  String responsiblePt,
                                                  String client,
                                                  String country,
                                                  TotalDaysStatisticsDto totalDaysStatistics,
                                                  List<ClientsList> clientsList) {

    public static DemoSingleConsultantResponseListDto toDto(
            DemoConsultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<ClientsList> clientsList) {
        return new DemoSingleConsultantResponseListDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getPhoneNumber(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                List.of(new ClientsList(consultant.getClient(), LocalDate.now(), LocalDate.now()))
        );
    }
}
