package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.service.MemberService;
import com.couchcoding.oola.service.StudyMemberSerivce;
import com.couchcoding.oola.service.StudyService;

import com.couchcoding.oola.util.RequestUtil;
import com.couchcoding.oola.validation.MemberNotFoundException;
import com.couchcoding.oola.validation.MemberUnAuthorizedException;
import com.couchcoding.oola.validation.ParameterBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;


@Slf4j
@RestController
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final MemberService memberService;
    private final StudyService studyService;
    private final StudyMemberSerivce studyMemberSerivce;

    @PostMapping("")
    public ResponseEntity<StudyResponseDto> createStudy(Authentication authentication,
                                                        @RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            throw  new ParameterBadRequestException(result);
        }

        Member member = ((Member) authentication.getPrincipal());
        String uid = member.getUid();

        Member memberFinded = memberService.findByUid(uid);
        if (memberFinded.equals(null)) {
            throw new MemberNotFoundException();
        }

        // 스터디 생성
        Study studyCreate = studyRequestDto.toEntity(studyRequestDto, uid, memberFinded);

        // 스터디에 참여하는 멤버 생성
        StudyMember studyMember = StudyMember.builder()
                .member(memberFinded)
                .study(studyCreate)
                .role("leader")
                .build();
        StudyResponseDto responseDto = studyService.createStudy(studyCreate);
        studyMemberSerivce.create(studyMember);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 스터디 단일 조회
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyDetail(Authentication authentication, @PathVariable @Valid Long studyId, HttpServletRequest request) throws ParseException {
        Member member = ((Member) authentication.getPrincipal());

        Study study = studyService.studyDetail(studyId);
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto = studyResponseDetailDto.toDto(study);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 수정 (단일 수정)
    @PatchMapping("/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyUpdate(Authentication authentication, @PathVariable Long studyId ,@RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ParameterBadRequestException(result);
        }

        Member member = ((Member) authentication.getPrincipal());
        String uid = member.getUid();

        Study updated = studyService.studyUpdate(studyId, studyRequestDto, uid);

        StudyResponseDetailDto studyResponseDetailDto = studyService.toDto(updated);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 완료시 수정
    @PatchMapping("/{studyId}/completion")
    public ResponseEntity<StudyResponseDetailDto> studyComplete(Authentication authentication, @PathVariable Long studyId, @RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new ParameterBadRequestException(bindingResult);
        }

        Member member = ((Member) authentication.getPrincipal());
        String uid = member.getUid();
        if (uid == null) {
            throw new MemberUnAuthorizedException();
        }

        Study updated = studyService.studyComplete(studyId, uid , studyRequestDto);

        StudyResponseDetailDto studyResponseDetailDto = studyService.toDto(updated);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }
}