package salt.consultanttracker.api.reddays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RedDayRepositoryTest {
    @Autowired
    RedDayRepository redDaysRepository;
    RedDay redDay;
    private RedDayKey redDayKey;

    @BeforeEach
    public void setUp() {
        redDay = new RedDay(new RedDayKey(
                LocalDate.parse("2024-01-01"), "SE"),
                "New Year");
        RedDay savedMockedData = redDaysRepository.save(redDay);
        redDayKey = savedMockedData.getId();
    }

    @Test
    @DisplayName("save")
    public void givenRedDay__whenSave__thenSuccessful() {
        redDay = new RedDay(new RedDayKey(
                LocalDate.parse("2024-01-01"), "SE"),
                "New Year");
        RedDay savedMockedData = redDaysRepository.save(redDay);
        redDayKey = savedMockedData.getId();
        RedDay actualResult = redDaysRepository.findById(redDayKey).orElse(null);
        assertNotNull(actualResult);
    }

    @Test
    @DisplayName("findAll")
    public void whenFindAll__thenListSize2() {
        var mockedRedDays = new RedDay(new RedDayKey(LocalDate.parse("2024-01-01"), "SE"),
                "New Year");
        redDaysRepository.save(mockedRedDays);
        List<RedDay> actualResult = redDaysRepository.findAll();
        assertEquals(1, actualResult.size());
    }

    @Test
    @DisplayName("findAllByCountry")
    public void givenSE__whenFindAllByCountry__thenListSize1() {
        List<RedDay> actualResult = redDaysRepository.findAllById_CountryCode("SE");
        assertEquals(1, actualResult.size());
    }

    @Test
    @DisplayName("findAllByCountry")
    public void givenNO__whenFindAllByCountry__thenEmptyList() {
        List<RedDay> actualResult = redDaysRepository.findAllById_CountryCode("NO");
        assertTrue(actualResult.isEmpty());
    }
}
