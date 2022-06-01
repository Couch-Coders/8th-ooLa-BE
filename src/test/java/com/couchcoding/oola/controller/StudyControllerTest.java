package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("local")
@SpringBootTest
class StudyControllerTest {

    private static final String uid = "DpKLjE6P5bRd4aAqWzl1gnbaKHr1";
    private static final String studyType = "프론트엔드";
    private static final String studyName = "씹어먹자 HTML&CSS11";
    private static String studyDays = "평일";
    private static final String timeZone = "오후 12 ~ 18시";
    private static final int participants = 5;
    private static final Date startDate =  null;
    private static final Date endDate = null;
    private static final String openChatUrl = "https://open.kakao.com/o/gihbQV0d";
    private static final String studyIntroduce = "안녕하세요 HTML/CSS 스터디를 진행하고자 합니다";
    private static final String studyGoal = "HTML&CSS 마스터2";
    private static final String status = "모집중";
    private static final Integer currentParticipants = 1;

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
    @DisplayName("로컬 study create 테스트")
    void createStudy() throws Exception {
//        LocalDateTime past = LocalDateTime.of(2022,6,1,0,0,0);

        String date = "2022.06.06";
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date startDate = fDate.parse(date);

        String date2 = "2022.10.06";
        SimpleDateFormat fDate2 = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date endDate = fDate.parse(date2);


        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .memberUid(null)
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDate)
                .endDate(endDate)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        ResultActions resultActions = mockMvc.perform(
                post("/studies/creation")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
    }
    
    @Test
    @DisplayName("스터디 단건 조회 테스트")
    void selectStudy() throws Exception {

        int studyId = 26;
        ResultActions resultActions = mockMvc.perform(
                get("/studies/" + studyId)
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
    @DisplayName("스터디 수정 테스트")
    void updateStudy() throws Exception {
        int studyId = 9;

        String date = "2022.06.06";
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date startDate = fDate.parse(date);

        String date2 = "2022.10.06";
        SimpleDateFormat fDate2 = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date endDate = fDate2.parse(date2);


        studyDays = "주말";
        String status = "진행중";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .memberUid(null)
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDate)
                .endDate(endDate)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        ResultActions resultActions = mockMvc.perform(
                patch("/studies/" + studyId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());

    }


    @Test
    @DisplayName("스터디 종료시 수정 테스트")
    void updateCompleteStudy() throws Exception {
        int studyId = 9;

        String date = "2022.06.06";
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date startDate = fDate.parse(date);

        String date2 = "2022.10.06";
        SimpleDateFormat fDate2 = new SimpleDateFormat("yyyy.MM.dd");
        java.util.Date endDate = fDate2.parse(date2);

        String status = "완료";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .memberUid(null)
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDate)
                .endDate(endDate)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        ResultActions resultActions = mockMvc.perform(
                patch("/studies/" + studyId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }
}