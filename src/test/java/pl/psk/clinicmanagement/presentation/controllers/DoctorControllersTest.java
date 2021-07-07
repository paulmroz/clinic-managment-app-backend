package pl.psk.clinicmanagement.presentation.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import pl.psk.clinicmanagement.domain.user.Doctor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"pl.psk"})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class DoctorControllersTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getOneDoctorTest() throws Exception {
        val result = mockMvc.perform(
                get("/doctors/2")
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andReturn();

        val expectedDoctor = objectMapper.readValue(result.getResponse().getContentAsString(), Doctor.class);

        assertThat(expectedDoctor.getId()).isEqualTo(2);
    }

    @Test
    public void getAllDoctorsTest() throws Exception {
        val result = mockMvc.perform(
                get("/doctors")
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andReturn();

        val expectedDoctors = objectMapper.readValue(result.getResponse().getContentAsString(), Doctor[].class);

        assertThat(expectedDoctors.length).isEqualTo(1);
    }

}
