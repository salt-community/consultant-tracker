package com.example.backend.redDays;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.nager.NagerClient;
import com.example.backend.consultant.ConsultantService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
class RedDaysServiceTest {
    private RedDaysService redDaysService;
    @Mock
    private NagerClient workingDaysClient;
    @Mock
    private ConsultantService consultantService;
    @Mock
    private RedDaysRepository redDaysRepository;


    @BeforeEach
    public void setUp() {
        this.redDaysService = new RedDaysService(consultantService, redDaysRepository, workingDaysClient);
    }

    @Test
    public void shouldReturnOneAnd1JanuaryWhenCountrySE() {
        Mockito.when(redDaysRepository.findAllByCountry("SE")).thenReturn(RedDaysServiceMockedData.createMockedRedDaysSE());
        List<LocalDate> actualResult = redDaysService.getRedDays("SE");
        assertEquals(actualResult.size(), 1);
        assertEquals(actualResult.getFirst(), LocalDate.parse("2024-01-01"));
    }

    @Test
    public void shouldReturnTwoAnd24DecemberWhenCountryNO() {
        List<LocalDate> expectedResult = Lists.newArrayList(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-24"));
        Mockito.when(redDaysRepository.findAllByCountry("NO")).thenReturn(RedDaysServiceMockedData.createMockedRedDaysDataNO());
        List<LocalDate> actualResult = redDaysService.getRedDays("NO");
        assertEquals(actualResult.size(), 2);
        assertEquals(actualResult.get(1), expectedResult.get(1));
    }

    @Test
    public void shouldReturnFalseFor2JanuaryWhenCountrySE() {
        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573")))
                .thenReturn("SE");
        Mockito.lenient().when(redDaysRepository.findAllByCountry("SE")).thenReturn(RedDaysServiceMockedData.createMockedRedDaysSE());
        boolean actualResult = redDaysService.isRedDay(
                LocalDate.parse("2024-01-02"),
                UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"));
        assertFalse(actualResult);
    }
}