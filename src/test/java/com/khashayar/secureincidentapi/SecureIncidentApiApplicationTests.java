package com.khashayar.secureincidentapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecureIncidentApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void healthEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    void unknownIncidentReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/incidents/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void existingIncidentReturnsCorrectData() throws Exception {
        mockMvc.perform(get("/api/incidents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Suspicious login"))
                .andExpect(jsonPath("$.description")
                        .value("Several failed login attempts were detected."))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void incidentsEndpointReturnsBothIncidents() throws Exception {
        mockMvc.perform(get("/api/incidents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Suspicious login"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Phishing email"));
    }

    @Test
    @DirtiesContext
    void createdIncidentCanBeRetrieved() throws Exception {
        String requestBody = """
                {
                    "title": "Malware detected",
                    "description": "Antivirus detected a suspicious file."
                }
                """;

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/incidents/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Malware detected"))
                .andExpect(jsonPath("$.description")
                        .value("Antivirus detected a suspicious file."))
                .andExpect(jsonPath("$.status").value("OPEN"));

        mockMvc.perform(get("/api/incidents/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Malware detected"))
                .andExpect(jsonPath("$.description")
                        .value("Antivirus detected a suspicious file."))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
            {"title": "", "description": "A suspicious file was detected."}
            """,
            """
            {"title": "   ", "description": "A suspicious file was detected."}
            """,
            """
            {"description": "A suspicious file was detected."}
            """,
            """
            {"title": "Malware detected", "description": ""}
            """,
            """
            {"title": "Malware detected", "description": "   "}
            """,
            """
            {"title": "Malware detected"}
            """
    })
    @DirtiesContext
    void invalidIncidentIsRejectedWithoutBeingStored(String requestBody)
            throws Exception {
        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/incidents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}