package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperRegisteredTimeResponseDto;
import com.example.backend.client.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.registeredTime.RegisteredTime;
import com.example.backend.registeredTime.RegisteredTimeKey;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.backend.client.Activity.CONSULTANCY_TIME;
import static java.time.temporal.ChronoUnit.DAYS;


@Service
@RequiredArgsConstructor
public class ConsultantService {
    private final ConsultantRepository consultantRepository;
    private final TimekeeperClient timekeeperClient;
    private final RegisteredTimeService registeredTimeService;

    public ConsultantResponseListDto getAllConsultantDtos(int page, int pageSize) {
        Page<Consultant> consultantsList = getAllConsultantsPageable(page, pageSize);
        List<ConsultantResponseDto> consultantsDto = consultantsList.stream()
                .map(registeredTimeService::getConsultantsRegisteredTimeItems).toList();
        return new ConsultantResponseListDto(
                page,
                consultantsList.getTotalPages(),
                consultantsList.getTotalElements(),
                consultantsDto);
    }

//    public List<RegisteredTimeResponseDto> getConsultantTimeDto(List<RegisteredTime> consultantTimeDtoList) {
//        List<RegisteredTimeResponseDto> listOfRegisteredTime = new ArrayList<>();
//        UUID consultantId = consultantTimeDtoList.getFirst().getId().getConsultantId();
//        listOfRegisteredTime.add(getConsultancyTimeItemByConsultantId(consultantId));
//        consultantTimeDtoList.stream().map(el -> getGroupedConsultantsRegisteredTimeItems(el.getId().getConsultantId()));
//        // ACCOUNT FOR RED DAYS
//        RegisteredTimeResponseDto remainingConsultancyTimeByConsultantId = registeredTimeService.getRemainingConsultancyTimeByConsultantId(consultantId);
//        if (remainingConsultancyTimeByConsultantId != null) {
//            listOfRegisteredTime.add(remainingConsultancyTimeByConsultantId);
//        }
//        return listOfRegisteredTime;
//    }

//    private RegisteredTimeResponseDto getConsultancyTimeItemByConsultantId(UUID consultantId) {
//        List<RegisteredTime> firstAndLastRegisteredDateByConsultantId =
//                registeredTimeService.getFirstAndLastDateByConsultantId(consultantId);
//        return new RegisteredTimeResponseDto(
//                UUID.randomUUID(),
//                firstAndLastRegisteredDateByConsultantId.get(0).getId().getStartDate(),
//                firstAndLastRegisteredDateByConsultantId.get(1).getEndDate(),
//                CONSULTANCY_TIME.activity,
//                );
//    }

    public Page<Consultant> getAllConsultantsPageable(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return consultantRepository.findAllByActiveTrue(pageRequest);
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
                    Consultant consultant = new Consultant(
                            UUID.randomUUID(),
                            tkUser.firstName().trim().concat(" ").concat(tkUser.lastName().trim()),
                            tkUser.email(),
                            tkUser.phone(),
                            id,
                            tkUser.isActive()
                    );
                    createConsultant(consultant);
                }
            });
        }
        registeredTimeService.fetchRecordedTimeForConsultant();
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

}
