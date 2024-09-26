package salt.consultanttracker.api.responsiblept;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponsiblePTService {
    private final ResponsiblePTRepository saltUserRepository;

    public List<UUID> getAllPtsIds() {
        return saltUserRepository.findAllId();
    }

    public ResponsiblePT getSaltUserById(UUID key) {
        return saltUserRepository.findById(key).orElse(null);
    }

    public Set<String> getAllPtsNames() {
        Set<String> ptNamesList = saltUserRepository.findAllNames();
        return ptNamesList.isEmpty() ? new HashSet<>() : ptNamesList;
    }

    public ResponsiblePT getResponsiblePTByName(String ptName) {
        return saltUserRepository.findByFullName(ptName);
    }
}
