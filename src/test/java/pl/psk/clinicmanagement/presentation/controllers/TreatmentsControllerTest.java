package pl.psk.clinicmanagement.presentation.controllers;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import pl.psk.clinicmanagement.domain.treatments.Treatment;
import pl.psk.clinicmanagement.presentation.requests.ClientRequest;
import pl.psk.clinicmanagement.presentation.requests.TreatmentsRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"pl.psk"})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TreatmentsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getOneTreatatmentTest() throws Exception {
        val result = mockMvc.perform(
                get("/treatments/2")
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andReturn();

        val expectedTreatment = objectMapper.readValue(result.getResponse().getContentAsString(), Treatment.class);

        assertThat(expectedTreatment.getId()).isEqualTo(2);
    }

    @Test
    public void getAllTreatatmentTest() throws Exception {
        val result = mockMvc.perform(
                get("/treatments")
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andReturn();

        val expectedTreatments = objectMapper.readValue(result.getResponse().getContentAsString(), Treatment[].class);

        assertThat(expectedTreatments.length).isEqualTo(3);
    }

    @Test
    public void deleteTreatatmentTest() throws Exception {
        mockMvc
                .perform(delete("/treatments/3").contentType("application/json"))
                .andExpect(status().isNoContent());
    }


    @Test
    public void updateTreatatmentTest() throws Exception {
        val request = TreatmentsRequest
                .builder()
                .name("TestSpec1")
                .cost("200")
                .build();

        val result = mockMvc.perform(
                put("/treatments/2")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("200");
    }


    @Test
    public void saveTreatatmentTest() throws Exception {
        val request =  TreatmentsRequest
                .builder()
                .name("TestSpec1")
                .cost("200")
                .build();


        objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        val result = mockMvc.perform(
                post("/treatments")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("TestSpec1");

    }
}
