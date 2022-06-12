package com.couchcoding.oola.controller;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.service.StudyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mystudies")
public class MyStudyController {

    private final StudyMemberService studyMemberService;

    @GetMapping("/creation")
    public ResponseEntity<List<StudyMember>> mystudies(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        // role과 uid를 사용하여 검색
       List<StudyMember> studyMembers = studyMemberService.mystudies(member);
       return ResponseEntity.status(HttpStatus.OK).body(studyMembers);
    }

    @GetMapping("/progress")
    public ResponseEntity<List<StudyMember>> mystudiesProgress(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        List<StudyMember> studyMembers = studyMemberService.myJoinStudies(member);
        return ResponseEntity.status(HttpStatus.OK).body(studyMembers);
    }
}
