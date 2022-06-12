package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;

import com.couchcoding.oola.validation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.transaction.annotation.Transactional;
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

    //private static  final String uid = "9aPftjfIvFOtrFRNEgYUpyS04N83";
    //private static final String uid = "Ssx0Wx2oyfPPa3SnKA34qTzjEqF2";
    //private static final String uid = "abcd";
    //private static final String uid = "abcabcabcddddeefg";
    private static final String uid = "DpKLjE6P5bRd4aAqWzl1gnbaKHr1";
    //private static final String uid = "eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9nb29nbGUuaWRlbnRpdHkuaWRlbnRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsImV4cCI6MTY1NDUwNzM1NSwiaWF0IjoxNjU0NTAzNzU1LCJpc3MiOiJmaXJlYmFzZS1hZG1pbnNkay1qM3YzOUBvb2xhLW9hdXRoLmlhbS5nc2VydmljZWFjY291bnQuY29tIiwic3ViIjoiZmlyZWJhc2UtYWRtaW5zZGstajN2MzlAb29sYS1vYXV0aC5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsInVpZCI6ImFiY2RlZmcxMjMifQ.iiJcZf4vekGaVAkQI7Kq54cl3csv9dH0g4GsuZkfMXoRKsQnBGP1qT3fJAj-6lOupBd3zVmKL2hZ6cIKQmNKOuAaVnin9DbfqjBBghC7hMDvlXWIElR4l2uN5qbrsSGJsKN4sMBvaNq50lwKYNyHMThZ20wFzsQZH1SK5kxRHxdBdaYSoVD-Ow57P3kt3dUzd5y7vz9THlQR5t8Uso6zWOiCBHyvR_4Fn_hDpuo-wjftjkgPiXmcfqpI5AlyhBBNcx7tFfiqbJ4o_pK879A4DUsph2NXZSGC0P0wwXvDOUWM1vGmXeGLT6Xs69fze2b-7rsuvJxqV1-fZ4qazZjLZA";
    private static final String studyType = "프론트엔드";
    private static String studyName = "DO IT 자바스크립트 알고리즘3";
    private static String studyDays = "평일";
    private static final String timeZone = "오전 (9시 ~ 12시)";
    private static final int participants = 7;
    private static LocalDateTime startDate = null;
    private static LocalDateTime endDate = null;
    private static final String openChatUrl = "https://open.kakao.com/o/gihbQV0d";
    private static final String studyIntroduce = "안녕하세요 오전타임 React 스터디를 진행하고자 합니다";
    private static final String studyGoal = "React 마스터";
    private static final String status = "모집중";
    private static Integer currentParticipants = 1;

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
        String sdate = "2022-06-10 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-08-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName("기술면접 테스트3");
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);


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
        resultActions
                .andExpect(status().isCreated());
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
        int studyId = 42;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);


        String goal = "React 격파";

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(goal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(3);


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
        int studyId = 42;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-06-12 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        String status = "완료";

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);


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

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);


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

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);


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

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);

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

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);


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

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType(studyType);
        studyRequestDto.setStudyName(studyName);
        studyRequestDto.setStudyDays(studyDays);
        studyRequestDto.setTimeZone(timeZone);
        studyRequestDto.setParticipants(participants);
        studyRequestDto.setStartDate(startDateTime);
        studyRequestDto.setEndDate(endDateTime);
        studyRequestDto.setOpenChatUrl(openChatUrl);
        studyRequestDto.setStudyIntroduce(studyIntroduce);
        studyRequestDto.setStudyGoal(studyGoal);
        studyRequestDto.setStatus(status);
        studyRequestDto.setJoinStatus("leader");
        studyRequestDto.setCurrentParticipants(currentParticipants);


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

    @Test
    @DisplayName("스터디 참여 신청 테스트")
    @Transactional
    void 스터디참여신청테스트() throws Exception {
        Long studyId = 42L;

        ResultActions resultActions = mockMvc.perform(
                post("/studies/" + studyId + "/members")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디 참여자 조회 테스트")
    void 스터디참여자조회테스트() throws Exception {

        Long studyId = 35L;

        ResultActions resultActions = mockMvc.perform(
                get("/studies/" + studyId + "/members")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }
}