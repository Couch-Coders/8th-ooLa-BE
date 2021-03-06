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

    private static final String uid = "dy6dEPfvXEhG2lK0bgulLOIt2As1";
    private static final String studyType = "???????????????";
    private static String studyName = "React ????????????";
    private static String studyDays = "??????";
    private static final String timeZone = "?????? (6:00 - 12:00)";
    private static final int participants = 7;
    private static LocalDateTime startDate = null;
    private static LocalDateTime endDate = null;
    private static final String openChatUrl = "https://open.kakao.com/o/gihbQV0d";
    private static final String studyIntroduce = "??????????????? ???????????? ??????????????? ?????????";
    private static final String studyGoal = "React ?????????";
    private static final String status = "??????";
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
    @DisplayName("?????? study create ?????????")
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
    @DisplayName("????????? ?????? ?????? ?????????")
    void selectStudy() throws Exception {
        int studyId = 98;
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
    @DisplayName("????????? ????????? (??????) ?????????")
    void studySearch() throws Exception {
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "?????????");
        info.add("studyDays", "??????");
        info.add("timeZone", "?????? 12 ~ 18???");
        info.add("status", "?????????");

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
    @DisplayName("????????? ????????? (??????) ????????? - ???????????? 4???  + studyName ??????")
    void studySearchLogin() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "?????????");
        info.add("studyDays", "??????");
        info.add("timeZone", "?????? 12 ~ 18???");
        info.add("status", "?????????");
        info.add("studyName", "????????????");

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
    @DisplayName("????????? ????????? (??????) ????????? - ???????????? 3???")
    void studySearch2() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "?????????");
        info.add("studyDays", "??????");
        info.add("timeZone", "?????? 12 ~ 18???");

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
    @DisplayName("????????? ????????? (??????) ????????? - ???????????? 2??? + studyName ??????")
    void studySearch3() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "?????????");
        info.add("studyDays", "??????");
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
    @DisplayName("????????? ????????? (??????) ????????? - ???????????? ?????? + studyName ??????")
    void studySearch4() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("studyType", "?????????");
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
    @DisplayName("????????? ?????? ?????????")
    void updateStudy() throws Exception {
        int studyId = 98;

        String sdate = "2022-06-28 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-08-15 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);


        String goal = "React ???????????????";

        StudyRequestDto studyRequestDto = new StudyRequestDto();
        studyRequestDto.setCreateUid(uid);
        studyRequestDto.setStudyType("???????????????");
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
        studyRequestDto.setCurrentParticipants(2);


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
    @DisplayName("????????? ????????? ?????? ?????????")
    void updateCompleteStudy() throws Exception {
        int studyId = 42;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-06-12 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        String status = "??????";

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
    @DisplayName("????????? ?????? ???????????? ???????????? ???????????? ??????")
    void errorTest1() throws Exception {
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        studyDays = "??????";
        String status = "?????????";

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
    @DisplayName("????????? ????????? ????????? ???????????? ??????")
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
    @DisplayName("????????? ????????? ????????? ???????????? ??????")
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
    @DisplayName("???????????? ?????? ????????? ??????")
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
    @DisplayName("????????? ????????? ????????? ????????? ???????????? ???????????? ??????")
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
    @DisplayName("????????? ?????? ???????????? ???????????? ????????? ???????????? ??????")
    void errorTest7() throws Exception {
        int studyId = 35;

        String sdate = "2022-06-06 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-10-06 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        String status = "??????";

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
    @DisplayName("????????? ?????? ?????? ?????????")
    void ??????????????????????????????() throws Exception {
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
    @DisplayName("????????? ????????? ?????? ?????????")
    void ?????????????????????????????????() throws Exception {

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
    @DisplayName("????????? ????????? ????????? ?????? ?????? ?????????")
    void ????????????????????????????????????????????????() throws ParseException {
        String now =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

        // ?????? ??????
        System.out.println("?????? ??????: " + now);

        // ??????????????? ????????? ????????? ???????????? ????????? yyyy-MM-dd:hh:mm:ss (???????????? ???-???-???:00:00:00)
        String stDate = "2022-06-15 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(stDate, formatter2);
        System.out.println("String to LocalDatetime : " + startDateTime);

        // ?????? ??????
        // ?????? ???????????? ?????? ???????????? ??????????????? ?????? ????????? ??????
        if (startDateTime.isBefore(LocalDateTime.now())) {
            System.out.println("?????? ?????? ?????? ?????? ?????? ????????? ????????? ??????????????? ???????????? ????????????");
        } else {
            System.out.println("?????? ?????? ?????? ?????? ?????? ????????? ????????? ??????????????? ?????? ??????");
        }
    }
    
    @Test
    @DisplayName("????????? ????????? ?????? ?????????")
    void ?????????????????????????????????() throws Exception {
        String sdate = "2022-05-15 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(sdate, formatter);

        String edate = "2022-06-15 00:00:00";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDateTime = LocalDateTime.parse(edate, formatter2);

        if (startDateTime.isBefore(LocalDateTime.now()) && endDateTime.isBefore(LocalDateTime.now()) )  {
            System.out.println("?????? ?????? ?????? ?????? ?????? ????????? ????????? ???????????? ??? ??????????????? ???????????? ????????????");
        } else {
            System.out.println("?????? ?????? ?????? ?????? ?????? ????????? ????????? ???????????? ??? ??????????????? ?????? ??????");


            StudyRequestDto studyRequestDto = new StudyRequestDto();
            studyRequestDto.setCreateUid(uid);
            studyRequestDto.setStudyType(studyType);
            studyRequestDto.setStudyName("???????????? ?????????9");
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
    @DisplayName("????????? ????????? ???????????? ??????")
    void ????????????????????????????????????_?????????() throws Exception {
        Long studyId = 1L;

        StudyBlogRequestDto studyBlogRequestDto = new StudyBlogRequestDto();
        studyBlogRequestDto.setComment("1???????????? 5?????? ????????????");
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
    @DisplayName("????????? ????????? ???????????? ?????? ????????? - ????????? ???????????? ????????? ??????????????? ???????????? ??????")
    void ????????????????????????????????????_??????_?????????() throws Exception {
        // poipoipoiuyt??? 4??????????????? ?????? ???????????? ??????
        Long studyId = 1L;

        StudyBlogRequestDto studyBlogRequestDto = new StudyBlogRequestDto();
        studyBlogRequestDto.setComment("????????? ????????????");
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
    @DisplayName("????????? ???????????? ??????")
    void ?????????????????????_??????_?????????() throws Exception {

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
    @DisplayName("??????????????? ??????")
    void ??????_?????????_??????_?????????() throws Exception {
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
    @DisplayName("??????????????? ??????")
    void ??????_?????????_??????_?????????() throws Exception {
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
    @DisplayName("??????????????? ????????????")
    void ??????_?????????_????????????_?????????() throws Exception {

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
    @DisplayName("????????? ?????? ??????")
    void ?????????_??????_??????_?????????() throws Exception {

        Long studyId = 1L;

        StudyCommentRequestDto studyCommentRequestDto = new StudyCommentRequestDto("1??? ???????????? ?????? ????????? ??????");
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
    @DisplayName("????????? ?????? ?????? ??????")
    void ?????????_??????_??????_??????_?????????() throws Exception {

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
    @DisplayName("????????? ?????? ?????? ??????")
    void ?????????_??????_??????_??????_?????????() throws Exception {

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
    @DisplayName("????????? ?????? ?????? ?????????")
    void ?????????_??????_??????_?????????() throws Exception {

        Long studyId = 61L;
        Long commentId = 77L;
        CommentRequestDto commentRequestDto = new CommentRequestDto(commentId, "77??? ?????? ??????",null,"2oMPU4uFZwUWCvc7vuHM37JFlMk1", studyId );
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
    @DisplayName("????????? ?????? ?????? ?????????")
    void ?????????_??????_??????_?????????() throws Exception {
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