package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.repository.MemberRepository;
import com.couchcoding.oola.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.firebase.auth.FirebaseAuth;
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

    //private static final String uid = "DpKLjE6P5bRd4aAqWzl1gnbaKHr1";
    private static  String uid = "aaabbccdd";
    private static final String displayName = "장길동";
    private static final String email = "test@gmail.com";
    private static final String blogUrl = "https://junior-developer-myc.tistory.com/";
    private static final String githubUrl = "https://github.com/meeyoungchoi";
    private static final String photoUrl = "https://www.flaticon.com/free-icon/girl_146005";
    private static final String nickName = "testNickName";
    private static final String introduce = "안녕하세요 자기소개입니다";


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
        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .uid(customToken)
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
                        .header("Authorization", "Bearer " + uid)
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
}