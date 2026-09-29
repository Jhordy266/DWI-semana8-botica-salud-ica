package com.boticasaludica;

import com.boticasaludica.security.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Test
    void adminLoginDevuelveTokenDeCincoMinutos() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(jsonPath("$.role", is("ADMIN")))
                .andExpect(jsonPath("$.expiresIn", is(300)));
    }

    @Test
    void cajeroLoginDevuelveTokenDeDiezMinutos() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"cajero\",\"password\":\"cajero123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("cajero")))
                .andExpect(jsonPath("$.role", is("CAJERO")))
                .andExpect(jsonPath("$.expiresIn", is(600)));
    }

    @Test
    void credencialesIncorrectasDevuelven401() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"admin\",\"password\":\"mal\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void endpointProtegidoSinTokenDevuelve401() throws Exception {
        mockMvc.perform(get("/api/admin/prueba"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void tokenInvalidoDevuelve401() throws Exception {
        mockMvc.perform(get("/api/admin/prueba")
                        .header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void cajeroIntentandoEntrarAAdminDevuelve403() throws Exception {
        String loginJson = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"cajero\",\"password\":\"cajero123\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode login = objectMapper.readTree(loginJson);
        String token = login.get("token").asText();

        mockMvc.perform(get("/api/admin/prueba")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void jwtAdminContieneRolYExpiracionRealDeCincoMinutos() {
        var admin = User.withUsername("admin")
                .password("irrelevante")
                .roles("ADMIN")
                .build();

        String token = jwtService.generateToken(admin);

        assertThat(jwtService.extractUsername(token)).isEqualTo("admin");
        assertThat(jwtService.extractRole(token)).isEqualTo("ADMIN");

        Date issuedAt = jwtService.extractIssuedAt(token);
        Date expiresAt = jwtService.extractExpiration(token);

        long seconds = (expiresAt.getTime() - issuedAt.getTime()) / 1000L;
        assertThat(seconds).isEqualTo(300L);
    }

    @Test
    void jwtCajeroContieneRolYExpiracionRealDeDiezMinutos() {
        var cajero = User.withUsername("cajero")
                .password("irrelevante")
                .roles("CAJERO")
                .build();

        String token = jwtService.generateToken(cajero);

        assertThat(jwtService.extractUsername(token)).isEqualTo("cajero");
        assertThat(jwtService.extractRole(token)).isEqualTo("CAJERO");

        Date issuedAt = jwtService.extractIssuedAt(token);
        Date expiresAt = jwtService.extractExpiration(token);

        long seconds = (expiresAt.getTime() - issuedAt.getTime()) / 1000L;
        assertThat(seconds).isEqualTo(600L);
    }
}
