package com.example.backend.redDays;

import com.example.backend.client.nager.NagerClient;
import com.example.backend.consultant.ConsultantRepository;
import com.example.backend.consultant.ConsultantService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RedDaysServiceTest {
    private RedDaysService redDaysService;

    private NagerClient workingDaysClient;
    @Mock
    private ConsultantService consultantService;
    @Mock
    private ConsultantRepository consultantRepository;
    @Mock
    private RedDaysRepository redDaysRepository;


    @BeforeEach
    public void setUp() {
        this.redDaysService = new RedDaysService(consultantService, redDaysRepository, workingDaysClient);
    }

    @Test
    public void shouldReturnOneAnd1JanuaryWhenCountrySE() {
        var mockedRedDay = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "Red day test",
                "SE"
        );
        List<RedDays> mockedList = Lists.newArrayList(mockedRedDay);
        Mockito.when(redDaysRepository.findAllByCountry("SE")).thenReturn(mockedList);
        List<LocalDate> actualResult = redDaysService.getRedDays("SE");
        assertEquals(actualResult.size(), 1);
        assertEquals(actualResult.getFirst(), LocalDate.parse("2024-01-01"));
    }

    @Test
    public void shouldReturnTwoAnd24DecemberWhenCountryNO() {
        var mockedRedDay = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "Red day test",
                "NO"
        );
        var mockedRedDay2 = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-12-24"),
                "Red day test2",
                "NO"
        );
        var expectedResult = Lists.newArrayList(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-24"));
        List<RedDays> mockedList = Lists.newArrayList(mockedRedDay, mockedRedDay2);
        Mockito.when(redDaysRepository.findAllByCountry("NO")).thenReturn(mockedList);
        List<LocalDate> actualResult = redDaysService.getRedDays("NO");
        assertEquals(actualResult.size(), 2);
        assertEquals(actualResult.get(1), expectedResult.get(1));
    }

    @Test
    public void shouldReturnFalseFor2JanuaryWhenCountrySE() {
        var mockedRedDay = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "Red day test",
                "SE"
        );
        List<RedDays> mockedList = Lists.newArrayList(mockedRedDay);
        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573")))
                .thenReturn("SE");
        Mockito.lenient().when(redDaysRepository.findAllByCountry("SE")).thenReturn(mockedList);

        boolean actualResult = redDaysService.isRedDay(
                LocalDate.parse("2024-01-02"),
                UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"));
        assertFalse(actualResult);
    }

}