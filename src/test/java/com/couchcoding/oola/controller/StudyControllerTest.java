package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.validation.*;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
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
    //private static  final String uid = "abc";
    private static final String studyType = "알고리즘/자료구조";
    private static final String studyName = "DO IT 자바스크립트 알고리즘3";
    private static String studyDays = "평일";
    private static final String timeZone = "오전 9 ~ 12시";
    private static final int participants = 5;
    private static  LocalDateTime startDate =  null;
    private static  LocalDateTime endDate = null;
    private static final String openChatUrl = "https://open.kakao.com/o/gihbQV0d";
    private static final String studyIntroduce = "안녕하세요 오전타임 자바스크립트 알고리즘 스터디를 진행하고자 합니다 수정수정 테스트";
    private static final String studyGoal = "알고리즘 마스터";
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

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        ResultActions resultActions = mockMvc.perform(
                post("/studies")
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

        int studyId = 34;
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
    @DisplayName("스터디 필터링 (검색) 테스트")
    void studySearch() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "백엔드");
        info.add("studyDays", "주말");
        info.add("timeZone", "오후 12 ~ 18시");
        info.add("status", "모집중");

        ResultActions resultActions = mockMvc.perform(
                get("/studies")
                        .header("Authorization", "Bearer " + uid)
                        .params(info)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 필터링 (검색) 테스트 - 검색조건 4개  + studyName 검색")
    void studySearchLogin() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "백엔드");
        info.add("studyDays", "주말");
        info.add("timeZone", "오후 12 ~ 18시");
        info.add("status", "모집중");
        info.add("studyName", "씹어먹자");

        ResultActions resultActions = mockMvc.perform(
                get("/studies")
                        .header("Authorization", "Bearer " + uid)
                        .params(info)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 필터링 (검색) 테스트 - 검색조건 3개")
    void studySearch2() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "백엔드");
        info.add("studyDays", "주말");
        info.add("timeZone", "오후 12 ~ 18시");

        ResultActions resultActions = mockMvc.perform(
                get("/studies")
                        .header("Authorization", "Bearer " + uid)
                        .params(info)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 필터링 (검색) 테스트 - 검색조건 2개 + studyName 검색")
    void studySearch3() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "백엔드");
        info.add("studyDays", "주말");
        info.add("studyName", "NodeJS");

        ResultActions resultActions = mockMvc.perform(
                get("/studies")
                        .header("Authorization", "Bearer " + uid)
                        .params(info)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 필터링 (검색) 테스트 - 검색조건 한개 + studyName 검색")
    void studySearch4() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "백엔드");
        info.add("studyName", "springboot");

        ResultActions resultActions = mockMvc.perform(
                get("/studies")
                        .header("Authorization", "Bearer " + uid)
                        .params(info)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 수정 테스트")
    void updateStudy() throws Exception {
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        studyDays = "주말";
        String status = "진행중";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                //.memberUid(null)
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
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
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        String status = "완료";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
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
    @DisplayName("리더가 아닌 사용자가 스터디를 수정하는 경우")
    void errorTest1() throws Exception {
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        studyDays = "주말";
        String status = "진행중";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        mockMvc.perform(
                patch("/studies/" + studyId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(MemberForbiddenException.class)))
                ).andReturn();

    }


    @Test
    @DisplayName("스터디 생성시 잘못된 파라미터 요청")
    void errorTest2() throws Exception {

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(null)
                .studyName(null)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .openChatUrl(null)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        mockMvc.perform(
                post("/studies")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(
                (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(ParameterBadRequestException.class)))
        ).andReturn();
    }


    @Test
    @DisplayName("스터디 수정시 잘못된 파라미터 요청")
    void errorTest4() throws Exception {

        Integer studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(null)
                .studyName(null)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .openChatUrl(null)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        mockMvc.perform(
                patch("/studies/" + studyId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(ParameterBadRequestException.class)))
                ).andReturn();
    }


    @Test
    @DisplayName("존재하지 않는 스터디 조회")
    void errorTest3() throws Exception {

        Integer studyId = 50;

        mockMvc.perform(
                get("/studies/" + studyId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(StudyNotFoundException.class)))
        ).andReturn();
    }

    @Test
    @DisplayName("스터디 종료로 수정시 잘못된 파라미터 요청하는 경우")
    void errorTest6() throws Exception {
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        String status = "";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(null)
                .studyName(null)
                .studyDays(null)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        mockMvc.perform(
                patch("/studies/" + studyId + "/completion")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(ParameterBadRequestException.class)))
                ).andReturn();
    }


    @Test
    @DisplayName("리더가 아닌 사용자가 스터디를 완료로 수정하는 경우")
    void errorTest7() throws Exception {
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);
        
        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        String status = "완료";

        StudyRequestDto studyRequestDto = StudyRequestDto.builder()
                .studyType(studyType)
                .studyName(studyName)
                .studyDays(studyDays)
                .timeZone(timeZone)
                .participants(participants)
                .startDate(startDateTime)
                .endDate(endDateTime)
                .openChatUrl(openChatUrl)
                .studyIntroduce(studyIntroduce)
                .studyGoal(studyGoal)
                .status(status)
                .joinStatus("leader")
                .currentParticipants(currentParticipants)
                .build();

        String studyDtoJson = objectMapper.writeValueAsString(studyRequestDto);

        mockMvc.perform(
                patch("/studies/" + studyId + "/completion")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(
                        (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(MemberForbiddenException.class)))
                ).andReturn();
    }
}