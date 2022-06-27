package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studycomments.request.CommentRequestDto;
import com.couchcoding.oola.dto.studycomments.request.StudyCommentRequestDto;;
import com.couchcoding.oola.dto.studycomments.response.*;
import com.couchcoding.oola.entity.Comment;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyCommentRepository;
import com.couchcoding.oola.validation.CommentNotFoundException;
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
        List<Comment> comments = studyCommentRepository.findAllByStudyId(study.getStudyId());
        List<StudyMember> studyMembers = studyMemberService.studyMembers(study.getStudyId());

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

    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Member member,Long studyId, Long commentId) {
        String role = "";
        Study study = studyService.getStudy(studyId);
        List<StudyMember> studyMembers = study.getStudyMembers();
        for (StudyMember studyMember : studyMembers) {
            role = studyMember.getRole();
        }
        Comment comment = studyCommentRepository.findById(commentId).orElseThrow(() -> {
            throw  new CommentNotFoundException();
        });

        Comment entity = null;
        if (comment.getMember().getUid().equals(member.getUid())) {
            comment = comment.update(comment, commentRequestDto);
            entity  = studyCommentRepository.save(comment);
        } else {
            throw new MemberForbiddenException();
        }

        CommentResponseDto studyCommentsResponseDto = new CommentResponseDto(entity, role, member , study );
        return studyCommentsResponseDto;
    }

    public void deleteComment(Member member,  Long commentId) {
       Comment comment = studyCommentRepository.findById(commentId).orElseThrow(() -> {
           throw new CommentNotFoundException();
       });

       if (comment.getMember().getUid().equals(member.getUid())) {
           studyCommentRepository.delete(comment);
       } else {
           throw new MemberForbiddenException();
       }
    }
}
