package com.example.backend.consultant;

import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;

    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize, String name, String pt, String client) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize, name, pt, client);
        List<ConsultantResponseDto> consultantsDto = consultantsList.stream()
                .map(registeredTimeService::getConsultantTimelineItems).toList();
        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultantsDto);
    }

    public Page<Consultant> getAllConsultantsPageable(int page, int pageSize, String name, String pt, String client) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return consultantRepository.findAllByActiveTrueAndFilterByName(name, pageRequest);
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    //    @Scheduled(cron = "0 0 0 * * *")
    public void fetchDataFromTimekeeper() {
        List<TimekeeperUserDto> timekeeperUserDto = timekeeperClient.getUsers();
        assert timekeeperUserDto != null;
        List<TimekeeperUserDto> consultantsToAdd = checkTimekeeperUsersWithDatabase(timekeeperUserDto);
        if (!consultantsToAdd.isEmpty()) {
            consultantsToAdd.forEach(consultant -> {
                String countryTag = Tag.extractCountryTagFromTimekeeperUserDto(consultant);
                Consultant newConsultant = new Consultant(
                        UUID.randomUUID(),
                        consultant.firstName().trim().concat(" ").concat(consultant.lastName().trim()),
                        consultant.email(),
                        consultant.phone(),
                        consultant.id(),
                        consultant.responsiblePT(),
                        consultant.client(),
                        countryTag,
                        consultant.isActive()
                );
                createConsultant(newConsultant);
            });
        }
        registeredTimeService.fetchAndSaveTimeRegisteredByConsultant();
        fillClientAndResponsiblePt();
    }

    public List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    private List<TimekeeperUserDto> checkTimekeeperUsersWithDatabase(List<TimekeeperUserDto> timekeeperUserResponseDto) {
        List<TimekeeperUserDto> consultantsToAdd = new ArrayList<>();
        List<Consultant> consultants = getAllConsultants();
        timekeeperUserResponseDto.forEach(tkUser -> {
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                consultantsToAdd.add(tkUser);
            } else {
                consultants.stream()
                        .filter(consultant -> consultant.getTimekeeperId().equals(tkUser.id()))
                        .forEach(consultant -> {
                            if (consultant.isActive() != tkUser.isActive() || consultant.isActive() != tkUser.isEmployee()) {
                                consultant.setActive(tkUser.isActive() && tkUser.isEmployee());
                                consultantRepository.save(consultant);
                            }
                        });
            }
        });
        return consultantsToAdd;
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
