package com.couchcoding.oola.dto.studycomments.response;

import com.couchcoding.oola.entity.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyCommentsResponseDto {
    private List<StudyCommentMemberResponseDto> studyCommentMemberResponseDtos;
    private List<Comment> comments;


    public StudyCommentsResponseDto(List<StudyCommentMemberResponseDto> studyCommentMemberResponseDtos, List<Comment> comments) {
        this.studyCommentMemberResponseDtos = studyCommentMemberResponseDtos;
        this.comments = comments;
    }
}
