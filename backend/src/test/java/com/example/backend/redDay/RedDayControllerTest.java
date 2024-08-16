package com.example.backend.redDay;

import com.example.backend.redDay.dto.RedDaysResponseDto;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(RedDayController.class)
public class RedDayControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RedDayService redDayService;

    @Test
    void given__whenGetApiRedDays__thenReturn2List2OfDates() throws Exception {
        List<LocalDate> listRedDaysSE =  List.of(LocalDate.parse("2024-12-24"));
        List<LocalDate> listRedDaysNO =  List.of(LocalDate.parse("2024-12-25"));
        var mockedServiceData = new RedDaysResponseDto(listRedDaysSE,listRedDaysNO);
        Mockito.when(redDayService.getAllRedDays()).thenReturn(mockedServiceData);
        mockMvc.perform(get("/api/redDays"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.redDaysSE").value("2024-12-24"))
                .andExpect(jsonPath("$.redDaysNO").value("2024-12-25"));
    }
}
