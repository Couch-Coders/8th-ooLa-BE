package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.dto.member.response.MemberResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로컬 회원 가입 테스트용
    @PostMapping("/local")
    public ResponseEntity<MemberResponseDto> registerLocalMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {
       Member member = memberSaveRequestDto.toEntity(memberSaveRequestDto);
       MemberResponseDto responseDto = memberService.register(member);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(responseDto);
    }

    // 로그인
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> login(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        return ResponseEntity.ok(new MemberResponseDto(member));
    }
}
