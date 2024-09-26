package salt.consultanttracker.api.responsiblept;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ResponsiblePTRepository extends JpaRepository<ResponsiblePT, UUID> {

    @Query("SELECT t.id FROM ResponsiblePT t")
    List<UUID> findAllId();

    @Query("SELECT t.fullName FROM ResponsiblePT t")
    Set<String> findAllNames();

    ResponsiblePT findByFullName(String ptName);
}
