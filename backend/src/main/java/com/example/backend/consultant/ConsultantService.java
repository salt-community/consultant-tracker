package com.example.backend.consultant;

import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.*;
import com.example.backend.exceptions.ConsultantNotFoundException;
import com.example.backend.meetings_schedule.MeetingsScheduleService;
import com.example.backend.meetings_schedule.dto.MeetingsDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.saltUser.SaltUser;
import com.example.backend.saltUser.SaltUserService;
import com.example.backend.tag.Tag;
import com.example.backend.timeChunks.TimeChunksService;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.example.backend.consultant.NotClient.PGP;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;
    private final TimeChunksService timeChunksService;
    private final MeetingsScheduleService meetingsScheduleService;
    private final SaltUserService saltUserService;

    //-----------------------------COVERED BY TESTS ---------------------------------
    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize, String name, List<String> pt, List<String> client) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize, name, pt, client);

        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultantsList.stream()
                        .map(c -> ConsultantResponseDto.toDto(c,
                                registeredTimeService.getAllDaysStatistics(c.getId()),
                                timeChunksService.getTimeChunksByConsultant(c.getId()))).toList());
    }

    public InfographicResponseDto getInfographicsByPt(String pt) {
        int totalConsultants = consultantRepository.findAllByActiveTrue().size();
        int totalPtsConsultants = consultantRepository.countAllByActiveTrueAndResponsiblePT(pt);
        int totalPgpConsultants = consultantRepository.countAllByActiveTrueAndClient(PGP.value);
        return new InfographicResponseDto(totalConsultants, totalPtsConsultants, totalPgpConsultants);
    }

    public SingleConsultantResponseListDto getConsultantById(UUID id) {
        Consultant consultantById = consultantRepository.findById(id).orElseThrow(() -> new ConsultantNotFoundException("Consultant with such id not found."));
        TotalDaysStatisticsDto totalDaysStatistics = registeredTimeService.getAllDaysStatistics(id);
        List<ClientsListDto> clientsListDto = getClientListByConsultantId(id);
        List<MeetingsDto> meetings = meetingsScheduleService.getMeetingsDto(id);
        return SingleConsultantResponseListDto.toDto(consultantById, totalDaysStatistics, clientsListDto, meetings);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<ClientsListDto> getClientListByConsultantId(UUID consultantId) {
        List<ClientsListDto> clientsListDto = new ArrayList<>();
        List<String> distinctClients = registeredTimeService.getClientsByConsultantId(consultantId);
        for (String client : distinctClients) {
            if (!client.equalsIgnoreCase(PGP.value)
                    && !client.equalsIgnoreCase(NotClient.UPSKILLING.value)) {
                LocalDate startDate = registeredTimeService.getStartDateByClientAndConsultantId(client, consultantId);
                LocalDate endDate = registeredTimeService.getEndDateByClientAndConsultantId(client, consultantId);
                clientsListDto.add(new ClientsListDto(client, startDate, endDate));
            }
        }
        if (clientsListDto.size() > 1) {
            sortClientsListDtoByStartDateDesc(clientsListDto);
        }

        return clientsListDto;
    }

    private void sortClientsListDtoByStartDateDesc(List<ClientsListDto> clientsListDto) {
        clientsListDto.sort(new Comparator<ClientsListDto>() {
            public int compare(ClientsListDto o1, ClientsListDto o2) {
                if (o1.startDate() == null || o2.startDate() == null)
                    return 0;
                return o2.startDate().compareTo(o1.startDate());
            }
        });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public Page<Consultant> getAllConsultantsPageable(int page, int pageSize, String name, List<String> pt, List<String> client) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        if (pt.isEmpty()) {
            pt.addAll(getListOfAllClientsOrPts("pts"));
        }
        if (client.isEmpty()) {
            client.addAll(getListOfAllClientsOrPts("clients"));
        }
        return consultantRepository.findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(name, pageRequest, pt, client);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *")
    public void fetchDataFromTimekeeper() {
        List<TimekeeperUserDto> timekeeperUserDto = timekeeperClient.getUsers();
        assert timekeeperUserDto != null;
        updateConsultantTable(timekeeperUserDto);
        registeredTimeService.fetchAndSaveTimeRegisteredByConsultantDB();
        List<Consultant> allActiveConsultants = getAllActiveConsultants();
        timeChunksService.saveTimeChunksForAllConsultants(allActiveConsultants);
        fillClientAndResponsiblePt(allActiveConsultants);
    }

    // Test in integration tests
    private void updateConsultantTable(List<TimekeeperUserDto> timekeeperUserDto) {
        timekeeperUserDto.forEach(tkUser -> {
            /* *** METHOD BELOW IS TESTED SEPARATELY *** */
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                /* *** METHOD BELOW IS TESTED SEPARATELY *** */
                String countryTag = Tag.extractCountryTagFromTimekeeperUserDto(tkUser);
                /* *** METHOD BELOW IS TESTED SEPARATELY *** */
                saveConsultant(new Consultant(
                        UUID.randomUUID(),
                        tkUser.firstName().trim().concat(" ").concat(tkUser.lastName().trim()),
                        tkUser.email(),
                        tkUser.phone(),
                        tkUser.id(),
                        tkUser.responsiblePT(),
                        tkUser.client(),
                        countryTag,
                        tkUser.isActive()));
            } else {
                /* *** METHOD BELOW IS TESTED SEPARATELY *** */
                updateExistingConsultant(tkUser);
            }
        });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void updateExistingConsultant(TimekeeperUserDto tkUser) {
        List<Consultant> consultants = getAllConsultants();
        consultants.stream()
                .filter(consultant -> consultant.getTimekeeperId().equals(tkUser.id()))
                .forEach(consultant -> {
                    if (consultant.isActive() != tkUser.isActive() || consultant.isActive() != tkUser.isEmployee()) {
                        consultant.setActive(tkUser.isActive() && tkUser.isEmployee());
                        consultantRepository.save(consultant);
                    }
                    if (tkUser.tags() != null && !tkUser.tags().stream().filter(el -> el.getName().contains("På uppdrag")).toList().isEmpty()) {
                        consultant.setClient(null);
                        consultantRepository.save(consultant);
                    } else if (tkUser.tags() != null && !tkUser.tags().stream().filter(el -> el.getName().contains(PGP.value)).toList().isEmpty()) {
                        consultant.setClient(PGP.value);
                        consultantRepository.save(consultant);
                    }
                });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void saveConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public String getCountryCodeByConsultantId(UUID consultantId) {
        return consultantRepository.findCountryById(consultantId);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public void fillClientAndResponsiblePt(List<Consultant> listOfActiveConsultants) {
        String[] responsiblePts = {"Josefin Stål", "Anna Carlsson"};
        Random rand = new Random();
        listOfActiveConsultants.forEach(el -> {
            int rand_int1 = rand.nextInt(2);
            if (el.getClient() == null) {
                el.setClient(registeredTimeService.getCurrentClient(el.getId()).trim());
            }
            el.setResponsiblePT(responsiblePts[rand_int1]);
            consultantRepository.save(el);
        });
    }

    public ClientsAndPtsListDto getAllClientsAndPts() {
        Set<String> listOfClients = getListOfAllClientsOrPts("clients");
        Set<String> listOfPts = getListOfAllClientsOrPts("pts");
        return new ClientsAndPtsListDto(listOfClients, listOfPts);
    }

    public Set<String> getListOfAllClientsOrPts(String clientOrPt) {
        List<Consultant> activeConsultants = getAllActiveConsultants();
        Set<String> resultList = new TreeSet<>();
        for (Consultant consultant : activeConsultants) {
            if (clientOrPt.equals("pts")) {
                resultList.add(consultant.getResponsiblePT());
            } else if (clientOrPt.equals("clients") && !consultant.getClient().equals("PGP")) {
                resultList.add(consultant.getClient());
            }
        }
        return resultList;
    }

    public void updatePtsForConsultants(Map<UUID, List<String>> consultantsAndPts) {
        List<Consultant> activeConsultants = getAllActiveConsultants();
        for (var entry : consultantsAndPts.entrySet()) {
            SaltUser pt = saltUserService.getSaltUserById(entry.getKey());
            List<String> consultants = entry.getValue();
            for (String name : consultants) {
                String[] namesSplit = name.split(" ");
                activeConsultants.forEach(el ->
                {
                    if (el.getFullName().contains(namesSplit[0])
                            && el.getFullName().contains(namesSplit[namesSplit.length - 1])) {
                        el.setSaltUser(pt);
                        saveConsultant(el);
                    }
                });
            }
        }
    }
}
