package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.member.response.MemberProfileResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.repository.MemberRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MemberServiceTest {
    private static final String uid = "abcabcabcddddeee";
    private static final String displayName = "배길동";
    private static final String email = "test5@gmail.com";
    private static final String blogUrl = "https://junior-developer-myc.tistory.com/";
    private static final String githubUrl = "https://github.com/meeyoungchoi";
    private static final String photoUrl = "https://www.flaticon.com/free-icon/girl_146005";
    private static final String nickName = "testNickName5";
    private static final String introduce = "안녕하세요 자기소개 수정수정4";
    private static List<String> teckSteck = Arrays.asList("NodeJS", "React", "Javascript", "Python");

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void findByUidTest() {
        Member member = Member.builder()
                .uid(uid)
                .displayName(displayName)
                .email(email)
                .blogUrl(blogUrl)
                .githubUrl(githubUrl)
                .photoUrl(photoUrl)
                .nickName(nickName)
                .introduce(introduce)
                .techStack(teckSteck)
                .build();

        Member result =  memberRepository.findByUid(uid).orElseThrow(() -> {
            throw new UsernameNotFoundException("해당 회원이 존재하지 않습니다.");
        });

        assertThat(member).isEqualTo(result);
    }

    @Test
    @DisplayName("회원 프로필 조회")
    void 프로필조회테스트() {
        Member member = memberService.findByUid(uid);
        assertThat(member.getUid()).isEqualTo(uid);
    }
}