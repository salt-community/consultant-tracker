package salt.consultanttracker.api.consultant;

import salt.consultanttracker.api.responsiblept.ResponsiblePT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, UUID> {
    boolean existsByTimekeeperId(Long id);

    List<Consultant> findAllByActiveTrue();
    int countAllByActiveTrue();
    Optional<Consultant> findByTimekeeperId(Long id);

    @Query("SELECT t.country FROM Consultant t WHERE t.id = (:id)")
    Optional<String> findCountryById(UUID id);

    int countAllByActiveTrueAndResponsiblePT(ResponsiblePT responsiblePT);

    @Query("SELECT t FROM Consultant t WHERE t.active = true AND t.fullName iLIKE %:fullName% AND t.responsiblePT.fullName IN :ptList AND t.client IN :clientsList ORDER BY t.fullName ASC")
    Page<Consultant> findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(String fullName, Pageable pageable, List<String> ptList, List<String> clientsList);

    int countAllByActiveTrueAndClient(String client);

    Consultant findConsultantById(UUID id);
    List<Consultant> findAllByActiveTrueAndNotionIdIsNull();
    List<Consultant> findAllByActiveTrueAndNotionIdIsNotNull();

    @Query("SELECT distinct t.client FROM Consultant t WHERE t.active = true")
    Set<String> findDistinctClientsByActiveTrue();
}
