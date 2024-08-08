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
        List<Long> timekeeperIdsToAdd = checkTimekeeperUsersWithDatabase(timekeeperUserDto);
        if (!timekeeperIdsToAdd.isEmpty()) {
            timekeeperIdsToAdd.forEach(id -> {
                TimekeeperUserDto tkUser = timekeeperUserDto.stream()
                        .filter(u -> Objects.equals(u.id(), id)).findFirst().orElse(null);
                if (tkUser != null) {
                    List<Tag> countryTagList = tkUser.tags()
                            .stream()
                            .filter(el -> el.getName().trim().equals("Norge") || el.getName().trim().equals("Sverige")).toList();
                    String countryTag = !countryTagList.isEmpty() ? countryTagList.getFirst().getName().trim() : "Sverige";
                    Consultant consultant = new Consultant(
                            UUID.randomUUID(),
                            tkUser.firstName().trim().concat(" ").concat(tkUser.lastName().trim()),
                            tkUser.email(),
                            tkUser.phone(),
                            id,
                            tkUser.responsiblePT(),
                            tkUser.client(),
                            countryTag,
                            tkUser.isActive()
                    );
                    createConsultant(consultant);
                }
            });
        }
        registeredTimeService.fetchAndSaveTimeRegisteredByConsultant();
        fillClientAndResponsiblePt();
    }

    public List<Consultant> getAllActiveConsultants() {
        return consultantRepository.findAllByActiveTrue();
    }

    private List<Long> checkTimekeeperUsersWithDatabase(List<TimekeeperUserDto> timekeeperUserResponseDto) {
        List<Long> idsToAdd = new ArrayList<>();
        List<Consultant> consultants = getAllConsultants();
        timekeeperUserResponseDto.forEach(tkUser -> {
            if (!consultantRepository.existsByTimekeeperId(tkUser.id())) {
                idsToAdd.add(tkUser.id());
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
        return idsToAdd;
    }

    private void createConsultant(Consultant consultant) {
        consultantRepository.save(consultant);
    }

    public String getCountryCodeByConsultantId(UUID consultantId) {
        return consultantRepository.findCountryById(consultantId);
    }

    public void fillClientAndResponsiblePt(){
        String[] responsiblePts = {"Josefin Stål", "Anna Carlsson"};
        Random rand = new Random();
        List<Consultant> allActiveConsultants = getAllActiveConsultants();
        allActiveConsultants.forEach(el->{
            int rand_int1 = rand.nextInt(2);
            el.setClient(registeredTimeService.getCurrentClient(el.getId()));
            el.setResponsiblePT(responsiblePts[rand_int1]);
            consultantRepository.save(el);
        });
    }

}
