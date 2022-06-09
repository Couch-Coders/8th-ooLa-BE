package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.dto.member.response.MemberResponseDto;
import com.couchcoding.oola.entity.Language;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.MemberLanguage;
import com.couchcoding.oola.service.MemberService;

import com.couchcoding.oola.validation.MemberForbiddenException;
import com.couchcoding.oola.validation.ParameterBadRequestException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final FirebaseAuth firebaseAuth;

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<MemberResponseDto> registerMember(
             @RequestHeader("Authorization") String header,
             @RequestBody @Valid MemberSaveRequestDto memberSaveRequestDto) {
        // TOKEN을 가져온다.
        FirebaseToken decodedToken = memberService.decodeToken(header);

        // 사용자를 등록한다.
        Member memberRegister = Member.builder()
                .uid(decodedToken.getUid())
                .email(decodedToken.getEmail())
                .githubUrl(memberSaveRequestDto.getGithubUrl())
                .blogUrl(memberSaveRequestDto.getBlogUrl())
                .displayName(decodedToken.getName())
                .photoUrl(decodedToken.getPicture())
                .nickName(memberSaveRequestDto.getNickName())
                .introduce(memberSaveRequestDto.getIntroduce())
                .build();

        MemberResponseDto responseDto = memberService.register(
                memberRegister);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 로컬 회원 가입 테스트용
    @PostMapping("/local")
    public ResponseEntity<MemberResponseDto> registerLocalMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {
       List<String> list = memberSaveRequestDto.getTechSetck();
       Member member = memberSaveRequestDto.toEntity(memberSaveRequestDto , list);

        MemberResponseDto responseDto = memberService.register(member);
        //MemberResponseDto responseDto = memberService.register(member);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(responseDto);
    }

    // 로그인
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> login(Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return ResponseEntity.ok(new MemberResponseDto(member));
    }

    // 마이프로필 수정
    @PatchMapping("/me")
    public ResponseEntity<MemberResponseDto> memberProfileUpdate(Authentication authentication, @RequestBody MemberSaveRequestDto memberSaveRequestDto , BindingResult result) {
        if (result.hasErrors()) {
            throw new ParameterBadRequestException(result);
        }

        Member member = (Member) authentication.getPrincipal();
        String uid = member.getUid();

        if (!uid.equals(memberSaveRequestDto.getUid())) {
            throw new MemberForbiddenException();
        }

        MemberResponseDto responseDto = new MemberResponseDto(memberService.memberProfileUpdate(uid, memberSaveRequestDto));
        return ResponseEntity.ok(responseDto);
    }
}
