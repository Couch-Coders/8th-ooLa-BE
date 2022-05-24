package com.couchcoding.oola.controller;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.validation.CommentNotFoundException;
import com.couchcoding.oola.validation.ParameterBadRequestException;
import com.couchcoding.oola.validation.URLNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
    }

    @Test
    public void invalidMembmerDtoTest() throws Exception {
        Member memberDto = new Member("test", null, "githubUrl", "blogUrl", null);

        String memberDtoJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(
                post("/member").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(memberDtoJson)
        )
        .andExpect(
                (result) -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(ParameterBadRequestException.class))
        ).andReturn();
    }

    @Test
    public void customExceptionExtendsExceptionTest() throws Exception {

        String code = "9";
        mockMvc.perform(
                get("/exception/" + code)
                .accept(MediaType.APPLICATION_JSON)

        ).andExpect(
                (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(URLNotFoundException.class)))
        ).andReturn();
    }



}