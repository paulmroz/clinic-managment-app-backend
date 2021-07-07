package pl.psk.clinicmanagement.presentation.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.presentation.requests.UserEditRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"pl.psk"})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void updateUserTest() throws Exception {
        val request = UserEditRequest
                .builder()
                .email("TEST_EMAIL@EXAMPLE.ORG")
                .roles("ROLE_USER")
                .bornAt(LocalDateTime.now().toString())
                .city("Kielce")
                .firstName("Pawel")
                .houseNumber("12")
                .lastName("Mroz")
                .password("TEST")
                .passwordConfirmation("TEST")
                .phoneNumber("+123123123")
                .specialization("TEST")
                .street("SPORTOWA")
                .type("client")
                .build();

        val result = mockMvc.perform(
                put("/users/16")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Pawel").contains("Mroz");
    }
}
