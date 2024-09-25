package salt.consultanttracker.api.tag;

import salt.consultanttracker.api.ApplicationTestConfig;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperUserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
class TagTest {
    private static final Tag tagTest = new Tag();
    private static final Tag tagSverige = new Tag();
    private static final Tag tagNorge = new Tag();

    @BeforeAll
    static void setUp() {
        tagTest.setName("TestTag");
        tagSverige.setName("Sverige");
        tagNorge.setName("Norge");
    }

    @Test
    void shouldReturnSverige() {

        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                List.of(tagTest, tagSverige),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Sverige";
        String actualResult = Tag.extractCountryTagFromTimekeeperUserDto(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnNorge() {
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                List.of(tagTest, tagNorge),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Norge";
        String actualResult = Tag.extractCountryTagFromTimekeeperUserDto(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnSverigeForEmptyList() {
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                new ArrayList<>(),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Sverige";
        String actualResult = Tag.extractCountryTagFromTimekeeperUserDto(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnSverigeForListWithoutCountry() {
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                List.of(tagTest, tagSverige),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Sverige";
        String actualResult = Tag.extractCountryTagFromTimekeeperUserDto(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }
}