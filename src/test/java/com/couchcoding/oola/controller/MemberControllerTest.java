package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.dto.member.response.MemberProfileResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.repository.MemberRepository;
import com.couchcoding.oola.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("local")
@SpringBootTest
class MemberControllerTest {

    private static final String uid = "aaabbcc";
    //private static final String uid = "asdfasdf";
    private static final String displayName = "홍길동";
    private static final String email = "cmk664488@gmail.com";
    private static final String blogUrl = "ttps://shiningjean.tistory.com/35";
    private static final String githubUrl = "https://shiningjean.tistory.com/35";
    private static final String photoUrl = "https://lh3.googleusercontent.com/a/AATXAJy-nxiYfNUyNVazka8hszGGVnqO7sSKBX5TPs40=s96-c";
    private static final String nickName = "BackRookie";
    private static final String introduce = "안녕하세요 자기소개";
    private static List<String> techStack = Arrays.asList("React", "Typescript", "NodeJS" , "React Native");

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

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
    @DisplayName("서버 회원 가입 테스트")
    void registerMemberTestServer() throws Exception {
        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .uid(uid)
                .email(email)
                .githubUrl(githubUrl)
                .blogUrl(blogUrl)
                .displayName(displayName)
                .photoUrl(photoUrl)
                .nickName(nickName)
                .introduce(introduce)
                .build();


        System.out.println(memberSaveRequestDto);
        String memberDtoJson= objectMapper.writeValueAsString(memberSaveRequestDto);

        ResultActions resultActions = mockMvc.perform(
                post("/members")
                        .header("Authorization", "Bearer " + customToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(memberDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("uid").value(customToken))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("displayName").value(displayName))
                .andExpect(jsonPath("blogUrl").value(blogUrl))
                .andExpect(jsonPath("githubUrl").value(githubUrl))
                .andExpect(jsonPath("photoUrl").value(photoUrl));
    }

    @Test
    @DisplayName("로컬 회원 가입 테스트")
    void registerMemberTest() throws Exception {
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .uid(uid)
                .email(email)
                .githubUrl(githubUrl)
                .blogUrl(blogUrl)
                .displayName(displayName)
                .photoUrl(photoUrl)
                .nickName(nickName)
                .introduce(introduce)
                .techStack(techStack)
                .build();


        System.out.println("memberSaveRequestDto: " + memberSaveRequestDto.toString());
        String memberDtoJson= objectMapper.writeValueAsString(memberSaveRequestDto);
        System.out.println("JSON: " + memberDtoJson);

        ResultActions resultActions = mockMvc.perform(
                post("/members/local")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(memberDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("uid").value(uid))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("displayName").value(displayName))
                .andExpect(jsonPath("blogUrl").value(blogUrl))
                .andExpect(jsonPath("githubUrl").value(githubUrl))
                .andExpect(jsonPath("photoUrl").value(photoUrl));
    }

    @Test
    @DisplayName("로컬 로그인 테스트 id조회")
    public void id조회() {
        Optional<Member> byId = memberRepository.findByUid(uid);
        System.out.println("user:" + byId);
        assertThat(byId.get().getUid()).isEqualTo("DpKLjE6P5bRd4aAqWzl1gnbaKHr1");
    }

    @Test
    @DisplayName("로컬환경에서 로그인 테스트")
    void 로그인_테스트() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/members/me")
                        .header("Authorization", "Bearer " + "eyJhbGciOiJSUzI1NiIsImtpZCI6ImY5MGZiMWFlMDQ4YTU0OGZiNjgxYWQ2MDkyYjBiODY5ZWE0NjdhYzYiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoi7Zmp7Jyg7KeEIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBVFhBSnktbnhpWWZOVXlOVmF6a2E4aHN6R0dWbnFPN3NTS0JYNVRQczQwPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL29vbGEtb2F1dGgiLCJhdWQiOiJvb2xhLW9hdXRoIiwiYXV0aF90aW1lIjoxNjU1NTM3Njc0LCJ1c2VyX2lkIjoiVjlmcGFlQzNocmNsNE0yeG0wNUVGSDkybDRaMiIsInN1YiI6IlY5ZnBhZUMzaHJjbDRNMnhtMDVFRkg5Mmw0WjIiLCJpYXQiOjE2NTU1Mzc2NzQsImV4cCI6MTY1NTU0MTI3NCwiZW1haWwiOiJjbWs2NjQ0ODhAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMDk4MjY0NjA3OTI1NjQ4NTI5ODAiXSwiZW1haWwiOlsiY21rNjY0NDg4QGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6Imdvb2dsZS5jb20ifX0.e2od56rfumTxZUJUKNGiCCjtvoMVNV6I3F1h84ZD4adZvgxiPK5EhGYAdodezGYi4BCUW-63w7c1jXRwL8F1e9k_LV5Meh5RgzJs3fyFbgqbbMr_MobcXxI6RPefMGXcsoVb86cmtSR_jPZG2za-0-4BVmy2xTYSO-0yF1Us8hNUy-f4gtPaJWLmi_ZQFPkLlbd5GvE20zVp4VvsttIijD6lDuUmNGZC0rnOsJEovX1f4i-MqNvhNtK6RSgadjpoxmXCPH9N_T8GxhxmZxyQa2ieUROUe4qnpoNs2oDcgCE-r9o2HRZpaivoLsY7ytvlcNnNjz6RF5FoO2orBz0Lbg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("uid").value(uid))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("displayName").value(displayName))
                .andExpect(jsonPath("blogUrl").value(blogUrl))
                .andExpect(jsonPath("githubUrl").value(githubUrl))
                .andExpect(jsonPath("photoUrl").value(photoUrl));
    }


    @Test
    @DisplayName("회원 조회 테스트")
    void 마이프로필_조회_테스트() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/members/myprofile")
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
    @DisplayName("로컬환경에서 회원 마이프로필 수정 테스트")
    void 마이프로필_수정_테스트() throws Exception {
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .uid(uid)
                .email(email)
                .githubUrl(githubUrl)
                .blogUrl(blogUrl)
                .displayName(displayName)
                .photoUrl(photoUrl)
                .nickName(nickName)
                .introduce(introduce)
                .techStack(techStack)
                .build();


        String memberDtoJson = objectMapper.writeValueAsString(memberSaveRequestDto);

        ResultActions resultActions = mockMvc.perform(
                patch("/members/me")
                        .header("Authorization", "Bearer " + uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(memberDtoJson)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }
}