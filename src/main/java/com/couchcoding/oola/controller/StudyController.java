package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;;
import com.couchcoding.oola.service.MemberService;
import com.couchcoding.oola.service.StudyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;


@Slf4j
@RestController
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final MemberService memberService;
    private final StudyService studyService;

    @PostMapping("/creation")
    public ResponseEntity<StudyResponseDto> createStudy(Authentication authentication,
                                                        @RequestBody @Valid StudyRequestDto studyRequestDto) {

        Member member = ((Member) authentication.getPrincipal());
        String uid = member.getUid();

        Member memberFinded = memberService.findByUid(uid);
        log.info("member:{}", memberFinded.toString());

        // 스터디 생성
        Study studyCreate = studyRequestDto.toEntity(studyRequestDto);
        log.info("studyCreate: {}", studyCreate.toString());

        StudyResponseDto responseDto = studyService.createStudy(studyCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 스터디 단일 조회
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyDetail(Authentication authentication, @PathVariable Long studyId) throws ParseException {
        Member member = ((Member) authentication.getPrincipal());

        Study study = studyService.studyDetail(studyId);
        log.info("study: {}", study.toString());
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto = studyResponseDetailDto.toDto(study);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 수정 (단일 수정)
    @PatchMapping("/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyUpdate(Authentication authentication, @PathVariable Long studyId ,@RequestBody StudyRequestDto studyRequestDto) {
        Member member = ((Member) authentication.getPrincipal());
        String uid = member.getUid();

        Study updated = studyService.studyUpdate(studyId, studyRequestDto, uid);

        StudyResponseDetailDto studyResponseDetailDto = studyService.toDto(updated);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }

    // 스터디 완료시 수정
    @PatchMapping("/completion/{studyId}")
    public ResponseEntity<StudyResponseDetailDto> studyComplete(Authentication authentication, @PathVariable Long studyId, @RequestBody StudyRequestDto studyRequestDto) {
        Member member = ((Member) authentication.getPrincipal());
        String uid = member.getUid();

        Study updated = studyService.studyComplete(studyId, uid , studyRequestDto);

        StudyResponseDetailDto studyResponseDetailDto = studyService.toDto(updated);
        return ResponseEntity.status(HttpStatus.OK).body(studyResponseDetailDto);
    }
}
