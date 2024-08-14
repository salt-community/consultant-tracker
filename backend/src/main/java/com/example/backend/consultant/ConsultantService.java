package com.example.backend.consultant;

import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.tag.Tag;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
@Data
@AllArgsConstructor
public class ConsultantService {
    private ConsultantRepository consultantRepository;
    private TimekeeperClient timekeeperClient;
    private RegisteredTimeService registeredTimeService;

    //-----------------------------COVERED BY TESTS ---------------------------------
    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize, String name, String pt, String client) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize, name, pt, client);

        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                new ArrayList<>(consultantsList.stream()
                        .map(registeredTimeService::getConsultantTimelineItems).toList()));
    }

    public Page<Consultant> getAllConsultantsPageable(int page, int pageSize, String name, String pt, String client) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return consultantRepository.findAllByActiveTrueAndFilterByName(name, pageRequest);
    }
    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

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
            // method below is tested
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                // method below is tested
                String countryTag = Tag.extractCountryTagFromTimekeeperUserDto(tkUser);
                // method below is tested
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
                updateIsActiveForExistingConsultant(tkUser);
            }
        });
    }

    // Test in integration tests
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

    private void createConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    public String getCountryCodeByConsultantId(UUID consultantId) {
        return consultantRepository.findCountryById(consultantId);
    }

    public void fillClientAndResponsiblePt() {
        String[] responsiblePts = {"Josefin Stål", "Anna Carlsson"};
//        String[] responsiblePts = {"00ae7ec3-bbf8-4926-aadb-b8e7e4378341", "3ecb112d-d85d-40c4-a81f-762c9f2e5abc"};
        Random rand = new Random();
        List<Consultant> allActiveConsultants = getAllActiveConsultants();
        allActiveConsultants.forEach(el -> {
            int rand_int1 = rand.nextInt(2);
            el.setClient(registeredTimeService.getCurrentClient(el.getId()));
            el.setResponsiblePT(responsiblePts[rand_int1]);
            consultantRepository.save(el);
        });
    }

}
