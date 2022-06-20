package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studycomments.request.StudyCommentRequestDto;;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentDataDto;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentMemberResponseDto;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentResponseDto;
import com.couchcoding.oola.dto.studycomments.response.StudyCommentsResponseDto;
import com.couchcoding.oola.entity.Comment;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyCommentRepository;
import com.couchcoding.oola.validation.MemberForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyCommentService {
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;
    private final StudyCommentRepository studyCommentRepository;

    public Comment createComment( Member member, StudyCommentRequestDto studyCommentRequestDto , Long studyId) {
        Study study = studyService.getStudy(studyId);
        Comment comment = new Comment(member, study, studyCommentRequestDto);
        Comment result = studyCommentRepository.save(comment);
        return result;
    }

    public StudyCommentsResponseDto getCommentList(Long studyId) {
        Study study = studyService.getStudy(studyId);
        List<Comment> comments = study.getComments();
        List<StudyMember> studyMembers = studyMemberService.studyMembers(studyId);

        List<StudyCommentMemberResponseDto> studyCommentMemberResponseDtos = new ArrayList<>();
        for (StudyMember studyMember : studyMembers) {
            String role = studyMember.getRole();
            Member member = studyMember.getMember();
            StudyCommentMemberResponseDto studyCommentMemberResponseDto = new StudyCommentMemberResponseDto(role, member);
            studyCommentMemberResponseDtos.add(studyCommentMemberResponseDto);
        }
        StudyCommentsResponseDto studyCommentsResponseDto = new StudyCommentsResponseDto(studyCommentMemberResponseDtos , comments);
        return studyCommentsResponseDto;
    }
}
