package com.example.backend.consultant;

import com.example.backend.demo.demoConsultant.DemoConsultantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsultantController.class)
public class ConsultantControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConsultantService consultantService;
    @MockBean
    DemoConsultantService demoConsultantService;

//    @Test
//    @DisplayName("/api/consultants")
//    void givenNoFilterData__whenGetApiConsultants__thenReturn1ConsultantWithNameHermanGustav() throws Exception {
//        Mockito.when(consultantService.getAllConsultantDtos(any(Integer.class),
//                any(Integer.class),
//                any(String.class),
//                any(List.class),
//                any(List.class))).thenReturn(MockedConsultantController.createMockedConsultantResponseListSize2Dto());
//        mockMvc.perform(get("/api/consultants"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.consultants.length()").value(2))
//                .andExpect(jsonPath("$.consultants[0].fullName").value("Herman Gustav"));
//    }
//    @Test
//    @DisplayName("/api/consultants?name=Lynn")
//    void givenNameLynn__whenGetApiConsultants__thenReturn1ConsultantWithNameMichaelLynn() throws Exception {
//        Mockito.when(consultantService.getAllConsultantDtos(0,
//                10,
//                "Lynn",
//                List.of(),
//                List.of())).thenReturn(MockedConsultantController.createMockedConsultantResponseListSize1Dto());
//        mockMvc.perform(get("/api/consultants?name=Lynn"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.consultants.length()").value(1))
//                .andExpect(jsonPath("$.consultants[0].fullName").value("Michael Lynn"));
//    }
//
//    @Test
//    @DisplayName("/api/consultants?client=H%26M")
//    void givenClientHandM__whenGetApiConsultants__thenReturn1ConsultantWithNameHermanGustav() throws Exception {
//        Mockito.when(consultantService.getAllConsultantDtos(0,
//                10,
//                "",
//                List.of(),
//                List.of("H%26M"))).thenReturn(MockedConsultantController.createMockedConsultantResponseListSize11Dto());
//        mockMvc.perform(get("/api/consultants?client=H%26M"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.consultants.length()").value(1))
//                .andExpect(jsonPath("$.consultants[0].fullName").value("Herman Gustav"));
//    }
//    @Test
//    @DisplayName("/api/consultants?pt=Helga%20Rasmussen")
//    void givenPTHelgaRasmussen__whenGetApiConsultants__thenReturn1ConsultantWithNameMichaelLynn() throws Exception {
//        Mockito.when(consultantService.getAllConsultantDtos(0,
//                10,
//                "",
//                List.of("Helga%20Rasmussen"),
//                List.of())).thenReturn(MockedConsultantController.createMockedConsultantResponseListSize1Dto());
//        mockMvc.perform(get("/api/consultants?pt=Helga%20Rasmussen"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.consultants.length()").value(1))
//                .andExpect(jsonPath("$.consultants[0].fullName").value("Michael Lynn"));
//    }
//
//    @Test
//    @DisplayName("/api/consultants?name=m&pt=Helga%20Rasmussen&pt=Emmy%20Liselotte&client=H%26M&client=Swedbank")
//    void givenAllFilters__whenGetApiConsultants__thenReturn2Consultants() throws Exception {
//        Mockito.when(consultantService.getAllConsultantDtos(0,
//                10,
//                "m",
//                List.of("Helga%20Rasmussen", "Emmy%20Liselotte"),
//                List.of( "H%26M", "Swedbank"))).thenReturn(MockedConsultantController.createMockedConsultantResponseListSize2Dto());
//        mockMvc.perform(get("/api/consultants?name=m&pt=Helga%20Rasmussen&pt=Emmy%20Liselotte&client=H%26M&client=Swedbank"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.consultants.length()").value(2));
//    }
//
//    @Test
//    @DisplayName("api/consultant/getAllClientsAndPts")
//    void whenGetAllClientsAndPts__thenReturnListOf2ClientsAnd3Pts() throws Exception {
//        Mockito.when(consultantService.getAllClientsAndPts()).thenReturn(MockedConsultantController.createMockedClientsAndPtsResponseList());
//        mockMvc.perform(get("/api/consultants/getAllClientsAndPts"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.clients.length()").value(2))
//                .andExpect(jsonPath("$.pts.length()").value(3));
//    }
}
