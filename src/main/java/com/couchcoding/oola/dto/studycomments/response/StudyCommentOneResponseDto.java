package com.couchcoding.oola.dto.studycomments.response;

import com.couchcoding.oola.entity.Comment;
import lombok.Getter;

@Getter
public class StudyCommentOneResponseDto {
    private Comment comment;
    private StudyCommentMemberResponseDto studyCommentMemberResponseDto;

    public StudyCommentOneResponseDto(StudyCommentMemberResponseDto studyCommentMemberResponseDto, Comment comment) {
        this.studyCommentMemberResponseDto = studyCommentMemberResponseDto;
        this.comment = comment;
    }
}
