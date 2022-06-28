package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;

import com.couchcoding.oola.dto.studyblogs.request.StudyBlogRequestDto;
import com.couchcoding.oola.dto.studycomments.request.CommentRequestDto;
import com.couchcoding.oola.dto.studycomments.request.StudyCommentRequestDto;
import com.couchcoding.oola.dto.studylikes.request.StudyHateRequestDto;
import com.couchcoding.oola.dto.studylikes.request.StudyLikeRequestDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class StudyControllerTest {

    private static final String uid = "I6mKo2Qg8CMOhJ92X3wgiAlVlIh2";
    private static final String studyType = "프론트엔드";
    private static String studyName = "React 끝장내기";
    private static String studyDays = "주말";
    private static final String timeZone = "오전 (6:00 - 12:00)";
    private static final int participants = 7;
    private static LocalDateTime startDate = null;
    private static LocalDateTime endDate = null;
    private static final String openChatUrl = "https://open.kakao.com/o/gihbQV0d";
    private static final String studyIntroduce = "안녕하세요 스터디를 진행하고자 합니다";
    private static final String studyGoal = "React 마스터";
    private static final String status = "진행";
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
        String sdate = "2022-06-28 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-08-15 00:00:00";
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
        int studyId = 10;
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
        int studyId = 1;

        String sdate = "2022-06-17 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);


        String goal = "React 끝장";

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType("프론트엔드");
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
        studyRequestDto.setCurrentParticipants(1);


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

        Integer studyId = 60;

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
    void 스터디참여신청테스트() throws Exception {
        Long studyId = 98L;

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

        Long studyId = 1L;

        ResultActions resultActions = mockMvc.perform(
                get("/studies/" + studyId + "/members")
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
    @DisplayName("스터디 개설시 날짜에 따른 실패 테스트")
    void 스터디개설시날짜에따른실패테스트() throws ParseException {
        String now =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

        // 포맷 정의
        System.out.println("현재 시간: " + now);

        // 프론트에서 보내준 스터더 시작일자 형식은 yyyy-MM-dd:hh:mm:ss (값형식은 년-월-일:00:00:00)
        String stDate = "2022-06-15 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(stDate, formatter2);
        System.out.println("String to LocalDatetime : " + startDateTime);

        // 날짜 비교
        // 현재 날짜보다 이전 날짜이면 시작날짜가 될수 없어야 한다
        if (startDateTime.isBefore(LocalDateTime.now())) {
            System.out.println("현재 날짜 보다 과거 날짜 이므로 스터디 시작날짜로 사용할수 없습니다");
        } else {
            System.out.println("현재 날짜 보다 이후 날짜 이므로 스터디 시작날짜로 사용 가능");
        }
    }
    
    @Test
    @DisplayName("스터디 개설시 날짜 테스트")
    void 스터디개설시날짜테스트() throws Exception {
        String sdate = "2022-05-15 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-06-15 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        if (startDateTime.isBefore(LocalDateTime.now()) && endDateTime.isBefore(LocalDateTime.now()) )  {
            System.out.println("현재 날짜 보다 과거 날짜 이므로 스터디 시작날짜 및 종료날짜로 사용할수 없습니다");
        } else {
            System.out.println("현재 날짜 보다 이후 날짜 이므로 스터디 시작날짜 및 종료날짜로 사용 가능");


            StudyRequestDto studyRequestDto = new StudyRequestDto();
            studyRequestDto.setCreateUid(uid);
            studyRequestDto.setStudyType(studyType);
            studyRequestDto.setStudyName("기술면접 테스트9");
            studyRequestDto.setStudyDays(studyDays);
            studyRequestDto.setTimeZone(timeZone);
            studyRequestDto.setParticipants(participants);
            studyRequestDto.setStartDate(startDateTime);
            studyRequestDto.setEndDate(endDateTime);
            studyRequestDto.setOpenChatUrl(openChatUrl);
            studyRequestDto.setStudyIntroduce(studyIntroduce);
            studyRequestDto.setStudyGoal(studyGoal);
            studyRequestDto.setStatus(status);
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
    }
    
    @Test
    @DisplayName("스터디 참여자 공유로그 추가")
    void 스터디참여자공유로그추가_테스트() throws Exception {
        Long studyId = 1L;

        StudyBlogRequestDto studyBlogRequestDto = new StudyBlogRequestDto();
        studyBlogRequestDto.setComment("1번스터디 5번쨰 공유로그");
        studyBlogRequestDto.setShareLink("https://recordsoflife.tistory.com/153");
        String studyBlogJson = objectMapper.writeValueAsString(studyBlogRequestDto);

        ResultActions resultActions = mockMvc.perform(
                post("/studies/" + studyId + "/blogs")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyBlogJson)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        resultActions.andExpect(status().isCreated());
    }


    @Test
    @DisplayName("스터디 참여자 공유로그 예외 테스트 - 스터디 참여자가 아닌데 공유로그를 추가하는 경우")
    void 스터디참여자공유로그추가_예외_테스트() throws Exception {
        // poipoipoiuyt가 4번스터디에 대한 공유로그 작성
        Long studyId = 1L;

        StudyBlogRequestDto studyBlogRequestDto = new StudyBlogRequestDto();
        studyBlogRequestDto.setComment("두번쨰 공유로그");
        studyBlogRequestDto.setShareLink("https://nodejs.org/en/");
        String studyBlogJson = objectMapper.writeValueAsString(studyBlogRequestDto);

       mockMvc.perform(
                post("/studies/" + studyId + "/blogs")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyBlogJson)
                        .accept(MediaType.APPLICATION_JSON)
        ) .andExpect(
                (result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(MemberForbiddenException.class)))
        ).andReturn();
    }

    @Test
    @DisplayName("스터디 공유로그 조회")
    void 스터디공유로그_조회_테스트() throws Exception {

        Long studyId = 1L;

        ResultActions resultActions = mockMvc.perform(
                get("/studies/" + studyId + "/blogs")
                        //.header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk());


    }
    
    @Test
    @DisplayName("관심스터디 등록")
    void 관심_스터디_등록_테스트() throws Exception {
        Long studyId = 18L;

        StudyLikeRequestDto studyLikeRequestDto = new StudyLikeRequestDto(studyId, true);
        String json = objectMapper.writeValueAsString(studyLikeRequestDto);


        ResultActions resultActions = mockMvc.perform(
                post("/studies/" + studyId + "/likes")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        resultActions.andExpect(status().isCreated());
    }


    @Test
    @DisplayName("관심스터디 해제")
    void 관심_스터디_해제_테스트() throws Exception {
        Long studyId = 61L;
        Long id = 75L;

        StudyHateRequestDto studyLikeRequestDto = new StudyHateRequestDto(id, studyId, true);
        String json = objectMapper.writeValueAsString(studyLikeRequestDto);
        ResultActions resultActions = mockMvc.perform(
                delete("/studies/" + studyId + "/hates")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("관심스터디 목록조회")
    void 관심_스터디_목록조회_테스트() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/mystudies/likes")
                        .header("Authorization", "Bearer " +uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 댓글 추가")
    void 스터디_댓글_추가_테스트() throws Exception {

        Long studyId = 1L;

        StudyCommentRequestDto studyCommentRequestDto = new StudyCommentRequestDto("1번 스터디에 대한 두번째 댓글");
        String studyBlogJson = objectMapper.writeValueAsString(studyCommentRequestDto);

        ResultActions resultActions = mockMvc.perform(
                post("/studies/" + studyId + "/comments")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(studyBlogJson)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
        resultActions.andExpect(status().isCreated());
    }


    @Test
    @DisplayName("스터디 댓글 목록 조회")
    void 스터디_댓글_목록_조회_테스트() throws Exception {

        Long studyId = 73L;

        ResultActions resultActions = mockMvc.perform(
                get("/studies/" + studyId + "/comments")
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
    @DisplayName("스터디 댓글 단건 조회")
    void 스터디_댓글_단건_조회_테스트() throws Exception {

        Long studyId = 66L;
        Long commentId = 37L;
        ResultActions resultActions = mockMvc.perform(
                get("/studies/" + studyId + "/comments/" + commentId)
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
    @DisplayName("스터디 댓글 수정 테스트")
    void 스터디_댓글_수정_테스트() throws Exception {

        Long studyId = 61L;
        Long commentId = 77L;
        CommentRequestDto commentRequestDto = new CommentRequestDto(commentId, "77번 댓글 수정",null,"2oMPU4uFZwUWCvc7vuHM37JFlMk1", studyId );
        String commentJson = objectMapper.writeValueAsString(commentRequestDto);
        ResultActions resultActions = mockMvc.perform(
                patch("/studies/" + studyId + "/comments/" + commentId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(commentJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 댓글 삭제 테스트")
    void 스터디_댓글_삭제_테스트() throws Exception {
        Long commentId = 1L;
        ResultActions resultActions = mockMvc.perform(
                delete("/studies/comments/" + commentId)
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk());
    }
}