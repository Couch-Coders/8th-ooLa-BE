package com.couchcoding.oola.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("local")
@SpringBootTest
class MyStudyControllerTest {
    private static final String uid = "aaabbcc";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }
    
    @Test
    @DisplayName("마이스터디 - 내가 개설한 스터디 조회")
    void 내가개설한스터디조회_테스트() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/mystudies/creation")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)

                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("마이스터디 - 내가 참여한 진행 스터디 조회")
    void 내가참여한진행스터디조회_테스트() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImY5MGZiMWFlMDQ4YTU0OGZiNjgxYWQ2MDkyYjBiODY5ZWE0NjdhYzYiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTWltaSBMZWUiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2hqb1hNalRjVXMtVnpJa3ItMGk4bTZiOTFFeGJpX0M2NjhnUjZrPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL29vbGEtb2F1dGgiLCJhdWQiOiJvb2xhLW9hdXRoIiwiYXV0aF90aW1lIjoxNjU1NjM5OTk0LCJ1c2VyX2lkIjoiMm9NUFU0dUZad1VXQ3ZjN3Z1SE0zN0pGbE1rMSIsInN1YiI6IjJvTVBVNHVGWndVV0N2Yzd2dUhNMzdKRmxNazEiLCJpYXQiOjE2NTU2Mzk5OTQsImV4cCI6MTY1NTY0MzU5NCwiZW1haWwiOiJpdnZ2eS5lQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE3NTAxMDQ1MjkyOTczNjI3MzA1Il0sImVtYWlsIjpbIml2dnZ5LmVAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.qGoPfKhfFbUzq8NJ1fqXJxPxrdLg1YwprIpbN9KmIZxKaWec3CNhUvWVr5I4JuKSPSAKO0JInuu-cp5IlPBbvTzJfXFxKxhuW6Mt1h13ndu0BuISmq9XxPd0ooucPdWW9lcFDazrqH-8otlBKedXHy_TPnR1RCabNE_9TJ7dIkqgOvy2N1OFXTd3eP-M6B-DRiui3741cg8k565bfCVYyDYBn_OOvXXKx2RhOrybCtmXpRm5TuywxjTfa7_i2trEQijQdQNv6ywWK2Coc4tBAuKd6AXrxYQImLaVzIzScFQbpU4MZgaDv8NemP8uRuwU_JWIRZRTZb4tiyxXpmisBg";
        ResultActions resultActions = mockMvc.perform(
                get("/mystudies/progress")
                        .header("Authorization", "Bearer " +  token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("마이스터디 - 내가 완료한 스터디 조회")
    void 내가_완료한_스터디_조회_테스트() throws Exception {
       // String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImY5MGZiMWFlMDQ4YTU0OGZiNjgxYWQ2MDkyYjBiODY5ZWE0NjdhYzYiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTWltaSBMZWUiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2hqb1hNalRjVXMtVnpJa3ItMGk4bTZiOTFFeGJpX0M2NjhnUjZrPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL29vbGEtb2F1dGgiLCJhdWQiOiJvb2xhLW9hdXRoIiwiYXV0aF90aW1lIjoxNjU1NjExNjM5LCJ1c2VyX2lkIjoiMm9NUFU0dUZad1VXQ3ZjN3Z1SE0zN0pGbE1rMSIsInN1YiI6IjJvTVBVNHVGWndVV0N2Yzd2dUhNMzdKRmxNazEiLCJpYXQiOjE2NTU2MTE2MzksImV4cCI6MTY1NTYxNTIzOSwiZW1haWwiOiJpdnZ2eS5lQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE3NTAxMDQ1MjkyOTczNjI3MzA1Il0sImVtYWlsIjpbIml2dnZ5LmVAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.T1KgP2uIIQBsgdRSgFaxRcLqBmpYlZiRODgj7lREawRm3I6kotRaJk0aNWgmQqDt9aG8qutW1xIKdNyoa1mUuHv8iqVE7DjXO7lkFQ-URe1qNRA3L4fbSxCOQHLLi2MTm2vyhWFUf9Q31OnC_YGu9os44mlMV77ugsMVWEVzFA_HmHB81pdP9P2_rmAvaBTJWMhsZ6iWs0lI2hcB36Fi7twx5atg48hBcKAtGkuIYJnIYxyshBbTgdAvriREaJQrkFHp2vOh7HrkcBlZTiewx-spzYZ9TmBbfPPLwZfuc7JIqrRhgQekvGINbeG2tYsQFQ0De_6z9Ak-i6Lu6kL4Aw";
        ResultActions resultActions = mockMvc.perform(
                get("/mystudies/completion")
                        .header("Authorization", "Bearer " + "2oMPU4uFZwUWCvc7vuHM37JFlMk1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk());
    }
}