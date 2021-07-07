package pl.psk.clinicmanagement.presentation.controllers;



import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import pl.psk.clinicmanagement.domain.treatments.Treatment;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.presentation.requests.ClientRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"pl.psk"})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ClientControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveClientTest() throws Exception {
        val request = new ClientRequest();
        request.setEmail("test@example.org");
        request.setType("patient");
        request.setSpecialization("test");
        request.setBornAt("1998-01-01");
        request.setPassword("test");
        request.setPasswordConfirmation("test");

        objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        val result = mockMvc.perform(
                post("/clients")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("test@example.org");

    }
}
