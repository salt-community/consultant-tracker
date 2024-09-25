package salt.consultanttracker.api.timechunks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeChunksRepository extends JpaRepository<TimeChunks, TimeChunksKey> {

    List<TimeChunks> findAllById_ConsultantIdOrderById_StartDateAsc(UUID consultantId);

    List<TimeChunks> findAllById_ConsultantIdAndType(UUID consultantId, String type);

    @Query("SELECT t FROM TimeChunks t WHERE t.id.consultantId = (:consultantId) AND t.projectName IN :projectName ORDER BY t.id.startDate ASC")
    List<TimeChunks> findAllById_ConsultantIdAndProjectNameOrderById_StartDateAsc(UUID consultantId, List<String> projectName);
}
