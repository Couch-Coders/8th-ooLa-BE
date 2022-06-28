package com.couchcoding.oola.controller;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;
import com.couchcoding.oola.dto.study.response.StudyRoleResponseDto;
import com.couchcoding.oola.dto.studyblogs.request.StudyBlogRequestDto;
import com.couchcoding.oola.dto.studyblogs.response.StudyBlogListResponseDto;
import com.couchcoding.oola.dto.studyblogs.response.StudyBlogResponseDto;

import com.couchcoding.oola.dto.studycomments.request.CommentRequestDto;
import com.couchcoding.oola.dto.studycomments.request.StudyCommentRequestDto;
import com.couchcoding.oola.dto.studycomments.response.CommentResponseDto;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentDataDto;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentMemberResponseDto;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentsResponseDto;
import com.couchcoding.oola.dto.studylikes.request.StudyHateRequestDto;
import com.couchcoding.oola.dto.studylikes.request.StudyLikeRequestDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeStatus;
import com.couchcoding.oola.dto.studymember.response.StudyMemberResponseDto;
import com.couchcoding.oola.entity.*;

import com.couchcoding.oola.service.StudyBlogService;
import com.couchcoding.oola.service.StudyCommentService;

import com.couchcoding.oola.service.StudyLikeService;
import com.couchcoding.oola.service.StudyMemberService;
import com.couchcoding.oola.service.StudyService;

import com.couchcoding.oola.util.RequestUtil;
import com.couchcoding.oola.validation.*;
import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/studies")
@RestController
public class StudyController {

    private final StudyService studyService;

    private final StudyMemberService studyMemberService;
    private final StudyBlogService studyBlogService;
    private final StudyLikeService studyLikeService;
    private final StudyCommentService studyCommentService;
    private final FirebaseAuth firebaseAuth;

    @PostMapping("")
    public ResponseEntity<StudyResponseDto> createStudy(Authentication authentication,
                                                        @RequestBody @Valid StudyRequestDto studyRequestDto, BindingResult result) {
        if (result.hasErrors() || studyRequestDto.getStartDate().isBefore(LocalDateTime.now()) && studyRequestDto.getEndDate().isBefore(LocalDateTime.now())) {
            throw  new ParameterBadRequestException(result);
        }

        Member member = ((Member) authentication.getPrincipal());
        StudyResponseDto responseDto = studyService.createStudy(studyRequestDto, member);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 스터디 단일 조회
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyRoleResponseDto> studyDetail( @PathVariable @Valid Long studyId, HttpServletRequest request)  {
        StudyRoleResponseDto studyRoleResponseDto = null;

        String header = null;
        try {
            header = RequestUtil.getAuthorizationToken(request);
        } catch (CustomException e) {
            studyRoleResponseDto = studyService.studyDetail(studyId);
        }

        if (header != null) {
            studyRoleResponseDto = studyService.studyDetail(studyId, header);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studyRoleResponseDto);
    }

    // 스터디 필터링 (조건검색)
    @GetMapping("")
    public Page<Study> studySearch( Pageable pageable, @RequestParam(value = "studyType", required = false ) String studyType,
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


    // 스터디 참여자 공유로그 추가
    @PostMapping("/{studyId}/blogs")
    public ResponseEntity<StudyBlogResponseDto> blogs(Authentication authentication, @RequestBody StudyBlogRequestDto studyBlogRequestDto , @PathVariable Long studyId) {
        Member member  = (Member) authentication.getPrincipal();
        StudyBlogResponseDto studyBlogResponseDto = studyBlogService.blogs(studyBlogRequestDto, member, studyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(studyBlogResponseDto);
    }

    // 스터디 공유로그 목록 조회
    @GetMapping("/{studyId}/blogs")
    public StudyBlogListResponseDto getBlog(@PathVariable Long studyId) {
        // 스터디 블로그를 작성한 사람이 스터디에서 어떤 역할인지 정보도 함께 달라고 하셔서 Study를 통짜로 넘긴다
        StudyBlogListResponseDto studyBlogListResponseDto = studyBlogService.getBlogs(studyId);
        return studyBlogListResponseDto;
    }

    // 스터디에 대한 관심등록 누르기
    @PostMapping("/{studyId}/likes")
    public ResponseEntity<StudyLikeResponseDto> likeMyStudy(Authentication authentication , @RequestBody StudyLikeRequestDto studyLikeRequestDto, @PathVariable Long studyId) {
        Member member = (Member) authentication.getPrincipal();
        StudyLikeResponseDto studyLikeResponseDto = studyLikeService.LikeMyStudy(member, studyId , studyLikeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studyLikeResponseDto);
    }


    // 스터디에 대한 관심등록 해제
    @DeleteMapping("/{studyId}/hates")
    public ResponseEntity hateMyStudy(Authentication authentication, @PathVariable Long studyId, @RequestBody StudyHateRequestDto studyHateRequestDto) {
        Member member = (Member) authentication.getPrincipal();
        studyLikeService.deleteMyStudy(member, studyId , studyHateRequestDto);
        return  ResponseEntity.status(HttpStatus.OK).body("관심등록 해제 완료");
    }

    // 스터디에 대한 댓글 추가
    @PostMapping("/{studyId}/comments")
    public ResponseEntity<Comment> createComment(Authentication authentication , @RequestBody StudyCommentRequestDto studyCommentRequestDto, @PathVariable Long studyId) {
        Member member = (Member) authentication.getPrincipal();
        Comment comment = studyCommentService.createComment(member, studyCommentRequestDto, studyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    // 스터디에 달린 댓글 목록 조회
    @GetMapping("/{studyId}/comments")
    public ResponseEntity<StudyCommentsResponseDto> getCommentList(@PathVariable Long studyId) {
        StudyCommentsResponseDto studyCommentsResponseDto = studyCommentService.getCommentList(studyId);
        return ResponseEntity.status(HttpStatus.OK).body(studyCommentsResponseDto);
    }

    // 스터디에 달린 댓글 수정
    @PatchMapping("/{studyId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(Authentication authentication, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long studyId, @PathVariable Long commentId) {
        Member member = (Member) authentication.getPrincipal();
        CommentResponseDto commentResponseDto = studyCommentService.updateComment(commentRequestDto, member, studyId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    // 스터디에 달린 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity deleteComment(Authentication authentication,  @PathVariable Long commentId) {
        studyCommentService.deleteComment((Member) authentication.getPrincipal(), commentId);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 완료");
    }
}