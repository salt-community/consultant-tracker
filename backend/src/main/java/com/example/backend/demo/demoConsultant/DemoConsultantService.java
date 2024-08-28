package com.example.backend.demo.demoConsultant;


import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.dto.*;
import com.example.backend.exceptions.ConsultantNotFoundException;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.timeChunks.TimeChunksService;
import lombok.AllArgsConstructor;
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
    TimeChunksService timeChunksService;
    ConsultantService consultantService;

    public ConsultantResponseListDto getAllDemoConsultantDtos(int page, int pageSize, String name, List<String> pt, List<String> client) {
        Page<DemoConsultant> consultantsList = getAllDemoConsultantsPageable(page, pageSize, name, pt, client);

        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultantsList.stream()
                        .map(c -> ConsultantResponseDto.toDto(c,
                                registeredTimeService.getAllDaysStatistics(c.getId()),
                                timeChunksService.getTimeChunksByConsultant(c.getId()))).toList());
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

//    public SingleConsultantResponseListDto getDemoConsultantById(UUID id) {
//        DemoConsultant consultantById = demoConsultantRepo.findById(id).orElseThrow(() -> new ConsultantNotFoundException("Consultant with such id not found."));
//        TotalDaysStatisticsDto totalDaysStatistics = registeredTimeService.getAllDaysStatistics(id);
//        List<ClientsListDto> clientsList = consultantService.getClientListByConsultantId(id);
//        return SingleConsultantResponseListDto.toDto(consultantById, totalDaysStatistics, clientsList);
//    }
}
