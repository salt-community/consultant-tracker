package salt.consultanttracker.api.consultant;

import salt.consultanttracker.api.client.notion.dtos.ConsultantsNProxyDto;
import salt.consultanttracker.api.client.timekeeper.TimekeeperClient;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperUserDto;
import salt.consultanttracker.api.consultant.dto.*;
import salt.consultanttracker.api.exceptions.ConsultantNotFoundException;
import salt.consultanttracker.api.meetings.MeetingsScheduleService;
import salt.consultanttracker.api.meetings.dto.MeetingsDto;
import salt.consultanttracker.api.messages.Messages;
import salt.consultanttracker.api.registeredtime.RegisteredTimeService;
import salt.consultanttracker.api.responsiblept.ResponsiblePT;
import salt.consultanttracker.api.responsiblept.ResponsiblePTService;
import salt.consultanttracker.api.tag.Tag;
import salt.consultanttracker.api.timechunks.TimeChunksService;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

import static salt.consultanttracker.api.consultant.NotClient.PGP;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;
    private final TimeChunksService timeChunksService;
    private final MeetingsScheduleService meetingsScheduleService;
    private final ResponsiblePTService saltUserService;
    private static final Logger LOGGER = Logger.getLogger(ConsultantService.class.getName());

    //-----------------------------COVERED BY TESTS ---------------------------------
    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize, String name, List<String> pt, List<String> client, boolean includePgp) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize, name, pt, client, includePgp);

        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultantsList.stream()
                        .map(c -> ConsultantResponseDto.toDto(c,
                                registeredTimeService.getAllDaysStatistics(c.getId()),
                                timeChunksService.getTimeChunksByConsultant(c.getId()))).toList());
    }

    public InfographicResponseDto getInfographicsByPt(String ptName) {
        int totalConsultants = consultantRepository.findAllByActiveTrue().size();
        ResponsiblePT pt = saltUserService.getResponsiblePTByName(ptName);
        int totalPtsConsultants = 0;
        if (pt != null) {
            totalConsultants = consultantRepository.countAllByActiveTrueAndResponsiblePT(pt);
        }
        int totalPgpConsultants = consultantRepository.countAllByActiveTrueAndClient(PGP.value);
        return new InfographicResponseDto(totalConsultants, totalPtsConsultants, totalPgpConsultants);
    }

    public SingleConsultantResponseListDto getConsultantById(UUID id) {
        Consultant consultantById = consultantRepository.findById(id)
                .orElseThrow(() -> new ConsultantNotFoundException(Messages.CONSULTANT_NOT_FOUND));
        TotalDaysStatisticsDto totalDaysStatistics = registeredTimeService.getAllDaysStatistics(id);
        List<ClientsListDto> clientsListDto = getClientListByConsultantId(id);
        List<MeetingsDto> meetings = meetingsScheduleService.getMeetingsDto(id);
        return SingleConsultantResponseListDto.toDto(consultantById, totalDaysStatistics, clientsListDto, meetings);
    }

    public Consultant getConsultantByIdAndReturnConsultant(UUID id) {
        return consultantRepository.findConsultantById(id);
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
    public Page<Consultant> getAllConsultantsPageable(
            int page,
            int pageSize,
            String name,
            List<String> ptNames,
            List<String> client,
            boolean includePgp) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        if (ptNames.isEmpty()) {
            // need PTs names
            ptNames.addAll(saltUserService.getAllPtsNames());
        }
        if (client.isEmpty()) {
            client.addAll(getListOfAllClients(includePgp));
        }
        return consultantRepository.findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(name, pageRequest, ptNames, client);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    //            @PostConstruct
    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Stockholm")
    public void fetchDataFromTimekeeper() {
        LOGGER.info("Starting fetching data from timekeeper");
        List<TimekeeperUserDto> timekeeperUserDto = timekeeperClient.getUsers();
        assert timekeeperUserDto != null;
        updateConsultantTable(timekeeperUserDto);
        registeredTimeService.fetchAndSaveTimeRegisteredByConsultantDB();
        LOGGER.info("Data fetched from timekeeper");

        List<Consultant> allActiveConsultants = getAllActiveConsultants();

        fillClients(allActiveConsultants);
        LOGGER.info("Clients and PTs filled");

        timeChunksService.saveTimeChunksForAllConsultants(allActiveConsultants);
        LOGGER.info("Chunks saved");

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
                        tkUser.id(),
                        null,
                        tkUser.isActive(),
                        tkUser.client(),
                        countryTag,
                        null));
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
                    }
                    if (tkUser.tags() != null
                            && !tkUser.tags().stream()
                            .filter(el -> el.getName().contains("På uppdrag")).toList().isEmpty()) {
                        consultant.setClient(null);
                    } else if (tkUser.tags() != null
                            && !tkUser.tags().stream()
                            .filter(el -> el.getName().contains(PGP.value)).toList().isEmpty()) {
                        consultant.setClient(PGP.value);
                    }
                    consultantRepository.save(consultant);
                });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void saveConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public String getCountryCodeByConsultantId(UUID consultantId) {
        return consultantRepository.findCountryById(consultantId).orElse("Sverige");
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public void fillClients(List<Consultant> listOfActiveConsultants) {
        listOfActiveConsultants.forEach(el -> {
            if (el.getClient() == null) {
                el.setClient(registeredTimeService.getCurrentClient(el.getId()).trim());
            }
            consultantRepository.save(el);
        });
    }

    public ClientsAndPtsListDto getAllClientsAndPts(boolean includePgp) {
        Set<String> listOfClients = getListOfAllClients(includePgp);
        Set<String> listOfPts = saltUserService.getAllPtsNames();
        return new ClientsAndPtsListDto(listOfClients, listOfPts);
    }

    public Set<String> getListOfAllClients(boolean includePgp) {
        List<Consultant> activeConsultants = getAllActiveConsultants();
        Set<String> resultList = new TreeSet<>();
        if (includePgp) {
            activeConsultants.forEach(consultant -> resultList.add(consultant.getClient()));
        } else {
            activeConsultants.forEach(consultant -> {
                if (!consultant.getClient().equals("PGP")) {
                    resultList.add(consultant.getClient());
                }
            });
        }
        return resultList;
    }


    public void updateConsultantsTableWithNotionData(List<ConsultantsNProxyDto> listOfConsultants) {
        updateNotionIdInConsultantsTable(listOfConsultants);
    }

    private void updateNotionIdInConsultantsTable(List<ConsultantsNProxyDto> listOfNProxyConsultants) {
        List<Consultant> activeConsultants = consultantRepository.findAllByActiveTrueAndNotionIdIsNull();
        if (!activeConsultants.isEmpty()) {
            activeConsultants.forEach(consultant -> {
                        UUID uuid = updateProxyIdByConsultantName(consultant.getFullName(), listOfNProxyConsultants);
                        if(uuid != null){
                            consultant.setNotionId(uuid);
                        }
                    });
            consultantRepository.saveAll(activeConsultants);
        }

    }

    private UUID updateProxyIdByConsultantName(String fullName, List<ConsultantsNProxyDto> listOfNProxyConsultants) {
        List<ConsultantsNProxyDto> filteredListOfNProxyConsultant = listOfNProxyConsultants.stream()
                .filter(consultant -> areNamesMatching(fullName, consultant.name()))
                .toList();
        if(filteredListOfNProxyConsultant.isEmpty()){
            return null;
        }
        return filteredListOfNProxyConsultant.getFirst().id();
    }

    private boolean areNamesMatching(String fullName, String nProxyName) {
        String[] namesSplit = nProxyName.split(" ");
        return fullName.contains(namesSplit[0]) && fullName.contains(namesSplit[namesSplit.length - 1]);
    }
}
