package com.example.backend.demo.demoConsultant;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.dto.ClientsAndPtsListDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.TotalDaysStatisticsDto;
import com.example.backend.demo.dto.DemoConsultantResponseDto;
import com.example.backend.demo.dto.DemoConsultantResponseListDto;
import com.example.backend.exceptions.ConsultantNotFoundException;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DemoConsultantService {

    DemoConsultantRepository demoConsultantRepo;
    RegisteredTimeService registeredTimeService;

    public DemoConsultantResponseListDto getAllDemoConsultantDtos(int page, int pageSize, String name, List<String> pt, List<String> client) {
        Page<DemoConsultant> consultantsList = getAllDemoConsultantsPageable(page, pageSize, name, pt, client);

        return new DemoConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                new ArrayList<>(consultantsList.stream()
                        .map(registeredTimeService::getDemoConsultantTimelineItems).toList()));
    }

    public Page<DemoConsultant> getAllDemoConsultantsPageable(int page, int pageSize, String name, List<String> pt, List<String> client) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        if (pt.isEmpty()) {
            pt.addAll(getListOfAllDemoClientsOrPts("pts"));
        }
        if (client.isEmpty()) {
            client.addAll(getListOfAllDemoClientsOrPts("clients"));
        }
        return demoConsultantRepo.findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(name, pageRequest, pt, client);
    }

    public ClientsAndPtsListDto getAllDemoClientsAndPts() {
        Set<String> listOfClients = getListOfAllDemoClientsOrPts("clients");
        Set<String> listOfPts = getListOfAllDemoClientsOrPts("pts");
        return new ClientsAndPtsListDto(listOfClients, listOfPts);
    }

    public Set<String> getListOfAllDemoClientsOrPts(String clientOrPt) {
        List<DemoConsultant> activeConsultants = demoConsultantRepo.findAllByActiveTrue();
        Set<String> resultList = new TreeSet<>();
        for (DemoConsultant consultant : activeConsultants) {
            if (clientOrPt.equals("pts")) {
                resultList.add(consultant.getResponsiblePT());
            } else if (clientOrPt.equals("clients") && !consultant.getClient().equals("PGP")) {
                resultList.add(consultant.getClient());
            }
        }
        return resultList;
    }

    public DemoConsultantResponseDto getDemoConsultantById(UUID id) {
        DemoConsultant consultantById = demoConsultantRepo.findById(id).orElseThrow(() -> new ConsultantNotFoundException("Consultant with such id not found."));
        TotalDaysStatisticsDto totalDaysStatistics = registeredTimeService.getAllDaysStatistics(id);
        List<RegisteredTimeResponseDto> consultantTimeDto = registeredTimeService.getGroupedConsultantsRegisteredTimeItems(id);
        return DemoConsultantResponseDto.toDto(consultantById, totalDaysStatistics, consultantTimeDto);
    }
}
