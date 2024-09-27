package salt.consultanttracker.api.responsiblept;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salt.consultanttracker.api.client.notion.dtos.ConsultantsNProxyDto;
import salt.consultanttracker.api.client.notion.dtos.ResponsiblePTDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponsiblePTService {
    private final ResponsiblePTRepository responsiblePTRepository;

    public List<UUID> getAllPtsIds() {
        return responsiblePTRepository.findAllId();
    }

    public ResponsiblePT getSaltUserById(UUID key) {
        return responsiblePTRepository.findById(key).orElse(null);
    }

    public Set<String> getAllPtsNames() {
        Set<String> ptNamesList = responsiblePTRepository.findAllNames();
        return ptNamesList.isEmpty() ? new HashSet<>() : ptNamesList;
    }

    public ResponsiblePT getResponsiblePTByName(String ptName) {
        return responsiblePTRepository.findByFullName(ptName);
    }

    public void updateResponsiblePt(List<ResponsiblePTDto> dto) {
        List<UUID> listOfPtsIdsFromDB = getAllPtsIds();
        List<UUID> listOfPtsIdsFromNotion = dto.stream().map(ResponsiblePTDto::id).toList();
        listOfPtsIdsFromDB.stream()
                .filter(el -> !listOfPtsIdsFromNotion.contains(el))
                .forEach(responsiblePTRepository::deleteById);
        dto.stream()
                .filter(el -> !listOfPtsIdsFromDB.contains(el.id()))
                .forEach(el -> responsiblePTRepository.save(new ResponsiblePT(el.id(), el.name(), el.email(), "pt")));
    }
}
