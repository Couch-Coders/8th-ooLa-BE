package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.dto.member.response.MemberProfileResponseDto;
import com.couchcoding.oola.dto.member.response.MemberResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.repository.MemberRepository;
import com.couchcoding.oola.repository.impl.MemberRepositoryImpl;
import com.couchcoding.oola.util.RequestUtil;
import com.couchcoding.oola.validation.LoginForbiddenException;
import com.couchcoding.oola.validation.MemberForbiddenException;
import com.couchcoding.oola.validation.MemberNotFoundException;
import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

// UserDetailsService 인터페이스를 구현한다
// spring security에서 유저의 정보를 가져오기 위해 사용
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService  {

    private final MemberRepository memberRepository;
    //private final MemberRepositoryImpl memberRepositoryImpl;
    private final FirebaseAuth firebaseAuth;

    // 유저의 정보를 불러와서 UserDetails로 반환해준다
    // spring security에서 사용자의 정보를 담는 인터페이스

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        UserDetails user = memberRepository.loadUserByUsername(uid);
        if (user == null) {
            throw new LoginForbiddenException();
        }
        return user;
    }

    // 회원등록
    @Transactional
    public MemberResponseDto register(Member member) {
        validateAlreadyRegistered(member);
        return new MemberResponseDto(memberRepository.save(member));
    }

    // 이미 가입된 회원인지 검증
    private void validateAlreadyRegistered(Member member) {
        Optional<Member> optionalMember = memberRepository.findByUid(member.getUid());
        if (optionalMember.isPresent()) {
            throw new CustomException(ErrorCode.MemberExist);
        }
    }

    // 헤더에서 토큰을 꺼낸다
    public FirebaseToken decodeToken(String header) {
        try {
            log.debug("헤더: {}", header);
            String token = RequestUtil.getAuthorizationToken(header);
            return firebaseAuth.verifyIdToken(token);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            log.debug("저희 사이트 로그인시 헤더에서 토큰을 꺼낼때 예외발생: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    // 회원 단건 조회
    public MemberProfileResponseDto findByUid(String uid) {
        Member member = null;
        member =  memberRepository.findByUid(uid).orElseThrow((() -> {
            throw new MemberNotFoundException();
        }));
        return new MemberProfileResponseDto(member);
    }

    // 마이프로필 수정
    @Transactional
    public Member memberProfileUpdate(String uid, MemberSaveRequestDto memberSaveRequestDto) {
        Member updated = null;
        if (!uid.equals(memberSaveRequestDto.getUid())) {
            throw new MemberForbiddenException();
        }

        MemberProfileResponseDto result = findByUid(uid);
        updated = result.profileUpdate(uid, memberSaveRequestDto ,result.getMember().getId());
        updated = memberRepository.save(updated);
        return updated;
    }


}