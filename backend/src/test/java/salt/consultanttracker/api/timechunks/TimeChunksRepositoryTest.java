package salt.consultanttracker.api.timechunks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TimeChunksRepositoryTest {
    @Autowired
    TimeChunksRepository timeChunksRepository;
    @Test
    void shouldReturn_ListSize2() {
        UUID id = UUID.fromString("f0198a0b-6d1f-445e-ae00-31f6b8c40e45");
        LocalDateTime start1 = LocalDateTime.of(2024,5,10, 0, 0, 0);
        LocalDateTime end1 = LocalDateTime.of(2024,6,28, 23, 59, 59);
        LocalDateTime start2 = LocalDateTime.of(2024,6,29, 0, 0, 0);
        LocalDateTime end2 = LocalDateTime.of(2024,7,28,23, 59, 59);
        String client = "AstraZeneca";
        TimeChunks timeChunks1 = new TimeChunks(new TimeChunksKey(id,start1), "Konsult-Tid", end1, 20, client);
        TimeChunks timeChunks2 = new TimeChunks(new TimeChunksKey(id,start2), "Konsult-Tid", end2, 60, client);
        timeChunksRepository.save(timeChunks1);
        timeChunksRepository.save(timeChunks2);
        List<TimeChunks> actualResult = timeChunksRepository.findAllById_ConsultantIdAndProjectNameOrderById_StartDateAsc(id, List.of(client, "Remaining Days"));
        assertEquals(2, actualResult.size());
    }
}