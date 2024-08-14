package com.example.backend.consultant;

import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ClientsAndPtsListDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.tag.Tag;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Data
@AllArgsConstructor
public class ConsultantService {
    private ConsultantRepository consultantRepository;
    private TimekeeperClient timekeeperClient;
    private RegisteredTimeService registeredTimeService;

    //-----------------------------COVERED BY TESTS ---------------------------------
    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize, String name, String[] pt, String[] client) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize, name, pt, client);

        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                new ArrayList<>(consultantsList.stream()
                        .map(registeredTimeService::getConsultantTimelineItems).toList()));
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public Page<Consultant> getAllConsultantsPageable(int page, int pageSize, String name, String[] pt, String[] client) {
        Pageable pageRequest = PageRequest.of(page, pageSize);

//        return consultantRepository.findAllByActiveTrueAndFilterByName(name, pageRequest);

        System.out.println("name = " + name);
        System.out.println("listOfPt = " + List.of(pt));
        Page<Consultant> test = consultantRepository.findAllByActiveTrueAndFilterByName(name, pageRequest, List.of(pt));
        System.out.println("test = " + test);
        return test;
//        return consultantRepository.findAllByActiveTrueAndFullNameIsLikeIgnoreCaseAndResponsiblePTIn(name, pageRequest, List.of(pt));
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    //    @Scheduled(cron = "0 0 0 * * *")
    public void fetchDataFromTimekeeper() {
        List<TimekeeperUserDto> timekeeperUserDto = timekeeperClient.getUsers();
        assert timekeeperUserDto != null;
        updateConsultantTable(timekeeperUserDto);
        registeredTimeService.fetchAndSaveTimeRegisteredByConsultantDB();
        fillClientAndResponsiblePt();
    }

    // Test in integration tests
    private void updateConsultantTable(List<TimekeeperUserDto> timekeeperUserDto) {
        timekeeperUserDto.forEach(tkUser -> {
            /* *** METHOD BELOW IS TESTED SEPARATELY *** */
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                /* *** METHOD BELOW IS TESTED SEPARATELY *** */
                String countryTag = Tag.extractCountryTagFromTimekeeperUserDto(tkUser);
                /* *** METHOD BELOW IS TESTED SEPARATELY *** */
                createConsultant(new Consultant(
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
                updateIsActiveForExistingConsultant(tkUser);
            }
        });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void updateIsActiveForExistingConsultant(TimekeeperUserDto tkUser) {
        List<Consultant> consultants = getAllConsultants();
        consultants.stream()
                .filter(consultant -> consultant.getTimekeeperId().equals(tkUser.id()))
                .forEach(consultant -> {
                    if (consultant.isActive() != tkUser.isActive() || consultant.isActive() != tkUser.isEmployee()) {
                        consultant.setActive(tkUser.isActive() && tkUser.isEmployee());
                        consultantRepository.save(consultant);
                    }
                });
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    private void createConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public String getCountryCodeByConsultantId(UUID consultantId) {
        return consultantRepository.findCountryById(consultantId);
    }

    //-----------------------------COVERED BY TESTS ---------------------------------
    public void fillClientAndResponsiblePt() {
        String[] responsiblePts = {"Josefin Stål", "Anna Carlsson"};
        Random rand = new Random();
        List<Consultant> allActiveConsultants = getAllActiveConsultants();
        allActiveConsultants.forEach(el -> {
            int rand_int1 = rand.nextInt(2);
            el.setClient(registeredTimeService.getCurrentClient(el.getId()));
            el.setResponsiblePT(responsiblePts[rand_int1]);
            consultantRepository.save(el);
        });
    }

    public ClientsAndPtsListDto getAllClientsAndPts(){
        List<Consultant> activeConsultants = getAllActiveConsultants();
        Set<String> listOfClients = new TreeSet<>();
        Set<String> listOfPts = new TreeSet<>();
        for(Consultant consultant : activeConsultants){
            listOfClients.add(consultant.getClient());
            listOfPts.add(consultant.getResponsiblePT());
        }
        return new ClientsAndPtsListDto(listOfClients, listOfPts);
    }

}
