package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.response.StudyCompletionDto;
import com.couchcoding.oola.dto.study.response.StudyCreationDto;
import com.couchcoding.oola.dto.study.response.StudyProgressDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.service.StudyMemberService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.model.core.TypeRef;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mystudies")
public class MyStudyController {

    private final StudyMemberService studyMemberService;

    @GetMapping("/creation")
    public ResponseEntity<List<StudyCreationDto>> mystudies(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        // role과 uid를 사용하여 검색
       List<StudyCreationDto> studyCreationDtoList = studyMemberService.mystudies(member);
       return ResponseEntity.status(HttpStatus.OK).body(studyCreationDtoList);
    }

    @GetMapping("/progress")
    public ResponseEntity<List<StudyProgressDto>> mystudiesProgress(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        List<StudyProgressDto> studyProgressDtoList = studyMemberService.myJoinStudies(member);
        return ResponseEntity.status(HttpStatus.OK).body(studyProgressDtoList);
    }

    // 내가 완료한 스터디 조회
    @GetMapping("/completion")
    public ResponseEntity<List<StudyCompletionDto>> mystudiesCompletion(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        List<StudyCompletionDto> studyCompletionDtos  = studyMemberService.myStudiesCompletion(member);
        return ResponseEntity.status(HttpStatus.OK).body(studyCompletionDtos);
    }
}
