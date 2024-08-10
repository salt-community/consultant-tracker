package com.example.backend.redDays;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.nager.NagerClient;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.utils.Utilities;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

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
    private MockedStatic<Utilities> mockUtilities;


    @BeforeEach
    public void setUp() {
        this.redDaysService = new RedDaysService(consultantService, redDaysRepository, workingDaysClient);
    }

    @Test
    public void shouldReturnOneAnd1JanuaryWhenCountrySE() {
        Mockito.when(redDaysRepository.findAllByCountry("SE"))
                .thenReturn(RedDaysServiceMockedData.createMockedRedDayList());
        List<LocalDate> actualResult = redDaysService.getRedDays("SE");
        assertEquals(actualResult.size(), 1);
        assertEquals(actualResult.getFirst(), LocalDate.parse("2024-01-01"));
    }

    @Test
    public void shouldReturnTwoAnd24DecemberWhenCountryNO() {
        var expectedResult = Lists.newArrayList(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-24"));
        Mockito.when(redDaysRepository.findAllByCountry("NO"))
                .thenReturn(RedDaysServiceMockedData.createMockedRedDaysList());
        List<LocalDate> actualResult = redDaysService.getRedDays("NO");
        assertEquals(actualResult.size(), 2);
        assertEquals(actualResult.get(1), expectedResult.get(1));
    }

    @Test
    public void shouldReturnFalseFor2JanuaryWhenCountrySE() {
        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573")))
                .thenReturn("SE");
        Mockito.lenient().when(redDaysRepository.findAllByCountry("SE"))
                .thenReturn(RedDaysServiceMockedData.createMockedRedDayList());
        boolean actualResult = redDaysService.isRedDay(
                LocalDate.parse("2024-01-02"),
                UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"));
        assertFalse(actualResult);
    }


    @Test
    public void shouldReturn8JanuaryWhen5DaysRemainingAndStart1January() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573")))
                .thenReturn("Sverige");
        Mockito.lenient().when(redDaysRepository.findAllByCountry("SE"))
                .thenReturn(RedDaysServiceMockedData.createMockedRedDayList());
        mockUtilities = mockStatic(Utilities.class);
        mockUtilities.when(() -> Utilities.isWeekend(6)).thenReturn(true);
        mockUtilities.when(() -> Utilities.isWeekend(7)).thenReturn(true);
        mockUtilities.close();

        LocalDateTime expectedResult = LocalDateTime.parse("2024-01-08 23:59:59", formatter);
        LocalDateTime actualResult = redDaysService.removeNonWorkingDays(
                LocalDateTime.parse("2024-01-01 00:00:00", formatter),
                5,
                UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturn5WhenStartDay2JanAndDaysBetween15VariantMultiple() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573")))
                .thenReturn("Sverige");
        Mockito.lenient().when(redDaysRepository.findAllByCountry("SE"))
                .thenReturn(RedDaysServiceMockedData.createMockedRedDayList());
        mockUtilities = mockStatic(Utilities.class);
        mockUtilities.when(() -> Utilities.isWeekend(6)).thenReturn(true);
        mockUtilities.when(() -> Utilities.isWeekend(7)).thenReturn(true);
        mockUtilities.close();

        int actualResult = redDaysService.checkRedDaysOrWeekend(
               15L,
                LocalDate.parse("2023-12-31"),
                UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"),
                "multiple check");

        assertEquals(5, actualResult);
    }

    @Test
    public void shouldReturn1WhenStartDay2JanAndDaysBetween15VariantSingle() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573")))
                .thenReturn("Sverige");
        Mockito.lenient().when(redDaysRepository.findAllByCountry("SE"))
                .thenReturn(RedDaysServiceMockedData.createMockedRedDayList());
        mockUtilities = mockStatic(Utilities.class);
        mockUtilities.when(() -> Utilities.isWeekend(6)).thenReturn(true);
        mockUtilities.when(() -> Utilities.isWeekend(7)).thenReturn(true);
        mockUtilities.close();

        int actualResult = redDaysService.checkRedDaysOrWeekend(
                15L,
                LocalDate.parse("2023-12-31"),
                UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"),
                "single check");

        assertEquals(1, actualResult);
    }


}