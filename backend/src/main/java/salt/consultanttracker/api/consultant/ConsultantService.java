package salt.consultanttracker.api.consultant;

import org.springframework.transaction.annotation.Transactional;
import salt.consultanttracker.api.cache.CacheService;
import salt.consultanttracker.api.client.notion.NotionClient;
import salt.consultanttracker.api.client.notion.dtos.ConsultantsNProxyDto;
import salt.consultanttracker.api.client.notion.dtos.ResponsiblePTDto;
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

import static salt.consultanttracker.api.client.timekeeper.Activity.*;
import static salt.consultanttracker.api.utils.Country.SWEDEN;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;
    private final TimeChunksService timeChunksService;
    private final MeetingsScheduleService meetingsScheduleService;
    private final ResponsiblePTService responsiblePTService;
    private static final Logger LOGGER = Logger.getLogger(ConsultantService.class.getName());
    private final CacheService cacheService;
//    private final NotionClient notionClient; ALU

    //-----------------------------COVERED BY TESTS ---------------------------------

    public ConsultantResponseListDto getAllConsultantsDto(int page,
                                                          int pageSize,
                                                          String name,
                                                          List<String> pt,
                                                          List<String> client,
                                                          boolean includePgp) {
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
        int totalConsultants = consultantRepository.countAllByActiveTrue();
        ResponsiblePT pt = responsiblePTService.getResponsiblePTByName(ptName);
        int totalPtsConsultants = 0;
        if (pt != null) {
            totalConsultants = consultantRepository.countAllByActiveTrueAndResponsiblePT(pt);
        }
        int totalPgpConsultants = consultantRepository.countAllByActiveTrueAndClient(PGP.activity);
        return new InfographicResponseDto(totalConsultants, totalPtsConsultants, totalPgpConsultants);
    }

    public SingleConsultantResponseListDto getConsultantById(UUID consultantId) {
        Consultant consultantById = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new ConsultantNotFoundException(Messages.CONSULTANT_NOT_FOUND));
        TotalDaysStatisticsDto totalDaysStatistics = registeredTimeService.getAllDaysStatistics(consultantId);
        List<ClientsListDto> clientsListDto = getClientListByConsultantId(consultantId);
        List<MeetingsDto> meetings = meetingsScheduleService.getMeetingsDto(consultantId);
        return SingleConsultantResponseListDto.toDto(consultantById, totalDaysStatistics, clientsListDto, meetings);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<ClientsListDto> getClientListByConsultantId(UUID consultantId) {
        List<ClientsListDto> clientsListDto = new ArrayList<>();
        List<String> distinctClients = registeredTimeService.getClientsByConsultantId(consultantId);
        for (String client : distinctClients) {
            if (!client.equalsIgnoreCase(PGP.activity)
                    && !client.equalsIgnoreCase(UPSKILLING.activity)) {
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
            ptNames.addAll(responsiblePTService.getAllPtsNames());
        }
        if (client.isEmpty()) {
            client.addAll(getListOfAllClients(includePgp));
        }
        return consultantRepository.
                findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(name,
                        pageRequest,
                        ptNames,
                        client);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Stockholm")
    public void fetchDataFromTimekeeper() {
        LOGGER.info("Starting fetching data from timekeeper");
        List<TimekeeperUserDto> timekeeperUserDto = timekeeperClient.getUsers();
        assert timekeeperUserDto != null;
        updateConsultantTable(timekeeperUserDto);
        List<Consultant> allActiveConsultants = getAllActiveConsultants();
        registeredTimeService.fetchAndSaveTimeRegisteredByConsultantDB(allActiveConsultants);
        LOGGER.info("Data fetched from timekeeper");

        fillClients(allActiveConsultants);
        LOGGER.info("Clients field filled");

        timeChunksService.saveTimeChunksForAllConsultants(allActiveConsultants);
        LOGGER.info("Time chunks saved");
        cacheService.evictAllCaches();
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
                        null,
                        null));
            } else {
                /* *** METHOD BELOW IS TESTED SEPARATELY *** */
                updateExistingConsultant(tkUser);
            }
        });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void updateExistingConsultant(TimekeeperUserDto tkUser) {
        Consultant consultant = consultantRepository.findByTimekeeperId(tkUser.id())
                .orElseThrow(() -> new ConsultantNotFoundException(Messages.CONSULTANT_NOT_FOUND));
        boolean updated = false;
        if (consultant.isActive() != tkUser.isActive() || consultant.isActive() != tkUser.isEmployee()) {
            consultant.setActive(tkUser.isActive() && tkUser.isEmployee());
            updated = true;
        }
        if (tkUser.tags() != null
                && !tkUser.tags().stream()
                .filter(el -> el.getName().contains(ON_ASSIGNMENT.activity)).toList().isEmpty()) {
            consultant.setClient(null);
            updated = true;
        } else if (tkUser.tags() != null
                && !tkUser.tags().stream()
                .filter(el -> el.getName().contains(PGP.activity)).toList().isEmpty()) {
            consultant.setClient(PGP.activity);
            updated = true;
        }
        if (updated) {
            saveConsultant(consultant);
        }
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void saveConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public String getCountryCodeByConsultantId(UUID consultantId) {
        return consultantRepository.findCountryById(consultantId).orElse(SWEDEN.country);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public void fillClients(List<Consultant> listOfActiveConsultants) {
        listOfActiveConsultants.forEach(el -> {
            if (el.getClient() == null) {
                el.setClient(registeredTimeService.getCurrentClient(el.getId()).trim());
                saveConsultant(el);
            }
        });
    }

    public ClientsAndPtsListDto getAllClientsAndPts(boolean includePgp) {
        Set<String> listOfClients = getListOfAllClients(includePgp);
        Set<String> listOfPts = responsiblePTService.getAllPtsNames();
        return new ClientsAndPtsListDto(listOfClients, listOfPts);
    }

    public Set<String> getListOfAllClients(boolean includePgp) {
        Set<String> listOfClients = consultantRepository.findDistinctClientsByActiveTrue();
        if(!includePgp){
            listOfClients.remove(PGP.activity);
        }
        return listOfClients;
    }

    @Transactional
    public void updateConsultantsTableWithNotionData(List<ConsultantsNProxyDto> listOfConsultants) {
        updateResponsiblePTInConsultantsTable(listOfConsultants);
        updateNotionIdInConsultantsTable(listOfConsultants);
    }
//alu
//    public String getConsultantGithubURI(String consultantId) {
//        return notionClient.getClientGitHubFromNotion(consultantId);
//    }

    protected void updateResponsiblePTInConsultantsTable(List<ConsultantsNProxyDto> listOfNProxyConsultants) {
        List<Consultant> activeConsultants = consultantRepository.findAllByActiveTrueAndNotionIdIsNotNull();
        listOfNProxyConsultants.forEach(el -> {
            activeConsultants.forEach(consultant -> {
                if (el.id().equals(consultant.getNotionId())) {
                    List<ResponsiblePTDto> listOfResponsiblePTDto = el.listOfResponsiblePTs();
                    if (!listOfResponsiblePTDto.isEmpty()) {
                        ResponsiblePTDto responsiblePTDto = listOfResponsiblePTDto.getFirst();
                        ResponsiblePT responsiblePT = ResponsiblePTDto.toResponsiblePT(responsiblePTDto);
                        consultant.setResponsiblePT(responsiblePT);
                    }
                }
            });
        });
        consultantRepository.saveAll(activeConsultants);
    }

    protected void updateNotionIdInConsultantsTable(List<ConsultantsNProxyDto> listOfNProxyConsultants) {
        List<Consultant> activeConsultants = consultantRepository.findAllByActiveTrueAndNotionIdIsNull();
        if (!activeConsultants.isEmpty()) {
            activeConsultants.forEach(consultant -> {
                UUID uuid = updateProxyIdByConsultantName(consultant.getFullName(), listOfNProxyConsultants);
                String githubImageUrl = updateProxyGithubImageByConsultantName(consultant.getFullName(), listOfNProxyConsultants);
//                System.out.println("githubImageUrl = " + githubImageUrl);
//                consultant.setGithubImageUrl("https://github.com/sabinehernandes.png");
                consultant.setGithubImageUrl(githubImageUrl);

                if (uuid != null) {
                    consultant.setNotionId(uuid);
                }
            });
            consultantRepository.saveAll(activeConsultants);
            //alu
//            consultantRepository.flush();
        }
    }

    private UUID updateProxyIdByConsultantName(String fullName, List<ConsultantsNProxyDto> listOfNProxyConsultants) {
        List<ConsultantsNProxyDto> filteredListOfNProxyConsultant = listOfNProxyConsultants.stream()
                .filter(consultant -> areNamesMatching(fullName, consultant.name()))
                .toList();
        if (filteredListOfNProxyConsultant.isEmpty()) {
            return null;
        }
        return filteredListOfNProxyConsultant.getFirst().id();
    }

    private String updateProxyGithubImageByConsultantName(String fullName, List<ConsultantsNProxyDto> listOfNProxyConsultants) {
        List<ConsultantsNProxyDto> filteredListOfNProxyConsultant = listOfNProxyConsultants.stream()
                .filter(consultant -> areNamesMatching(fullName, consultant.name()))
                .toList();
        if (filteredListOfNProxyConsultant.isEmpty()) {
            return null;
        }
//        System.out.println("filteredListOfNProxyConsultant = " + filteredListOfNProxyConsultant.toString());
        return filteredListOfNProxyConsultant.getFirst().githubImageUrl();
    }

    private boolean areNamesMatching(String fullName, String nProxyName) {
        String[] namesSplit = nProxyName.split(" ");
        return fullName.contains(namesSplit[0]) && fullName.contains(namesSplit[namesSplit.length - 1]);
    }
}
