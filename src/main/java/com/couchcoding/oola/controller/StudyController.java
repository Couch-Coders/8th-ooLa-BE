package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;
import com.couchcoding.oola.dto.studymember.response.StudyMemberResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.service.StudyMemberService;
import com.couchcoding.oola.service.StudyService;

import com.couchcoding.oola.validation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/studies")
@RestController
public class StudyController {

    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    @PostMapping("")
    public ResponseEntity<StudyResponseDto> createStudy(Authentication authentication,
                                                        @RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            throw  new ParameterBadRequestException(result);
        }

        // 스터디 생성
        Member member = ((Member) authentication.getPrincipal());
        Study studyCreate = studyRequestDto.toEntity(studyRequestDto, member);

        // 스터디에 참여하는 멤버 생성
        StudyMember studyMember = StudyMember.builder()
                .member(member)
                .study(studyCreate)
                .role("leader")
                .build();
        StudyResponseDto responseDto = studyService.createStudy(studyCreate);
        studyMemberService.setStudyLeader(studyMember);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 스터디 단일 조회
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyDetail( @PathVariable @Valid Long studyId, HttpServletRequest request) throws ParseException {
        Study study = studyService.studyDetail(studyId);
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto = studyResponseDetailDto.toDto(study);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 필터링 (조건검색)
    @GetMapping("")
    public Page<Study> studySearch(Pageable pageable, @RequestParam(value = "studyType", required = false ) String studyType,
                                   @RequestParam( value = "studyDays", required = false)  String studyDays, @RequestParam(value = "timeZone", required = false) String timeZone
            , @RequestParam(value = "status",required = false)  String status, @RequestParam(value = "studyName", required = false) String studyName) {
        Page<Study> searchResult = studyService.findByAllCategory(pageable, studyType, studyDays, timeZone, status , studyName);
        if (searchResult.equals(null)) {
            throw new StudySearchNotFoundException();
        }
        return searchResult;
    }

    // 스터디 수정 (단일 수정)
    @PatchMapping("/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyUpdate(Authentication authentication, @PathVariable Long studyId ,@RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ParameterBadRequestException(result);
        }

        Member member = ((Member) authentication.getPrincipal());
        Study updated = studyService.studyUpdate(studyId, studyRequestDto, member);
        StudyResponseDetailDto studyResponseDetailDto = studyService.toDto(updated);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 완료시 수정
    @PatchMapping("/{studyId}/completion")
    public ResponseEntity<StudyResponseDetailDto> studyComplete(Authentication authentication, @PathVariable Long studyId, @RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ParameterBadRequestException(bindingResult);
        }

        Member member = ((Member) authentication.getPrincipal());
        Study updated = studyService.studyComplete(studyId, member , studyRequestDto);
        StudyResponseDetailDto studyResponseDetailDto = studyService.toDto(updated);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 참여자 정보 조회
    @GetMapping("/{studyId}/members")
    public List<StudyMember> stduyMembers(@PathVariable Long studyId) {
        return  studyMemberService.studyMembers(studyId);
    }

    // 스터디 참여 신청
    @PostMapping("/{studyId}/members")
    public ResponseEntity<StudyMemberResponseDto> studyMemberEnroll(Authentication authentication, @PathVariable Long studyId) {
        Member member = (Member) authentication.getPrincipal();
        StudyMemberResponseDto studyMemberResponseDto = studyMemberService.studyMemberEnroll(member , studyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(studyMemberResponseDto);
    }
}