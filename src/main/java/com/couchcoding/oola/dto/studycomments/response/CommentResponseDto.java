package com.couchcoding.oola.dto.studycomments.response;

import com.couchcoding.oola.entity.Comment;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private String content;
    private String role;
    private LocalDateTime createdDate;
    private String nickName;
    private String photoUrl;
    private Long studyId;
    private Long commentId;

    public CommentResponseDto(Comment comment, String role, Member member , Study study) {
        this.content = comment.getContent();
        this.role = role;
        this.createdDate = comment.getCreatedDate();
        this.nickName = member.getNickName();
        this.photoUrl = member.getPhotoUrl();
        this.studyId = study.getStudyId();
        this.commentId = comment.getId();
    }
}
