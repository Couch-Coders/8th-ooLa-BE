package com.couchcoding.oola.dto.studycomments.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private String uid;
    private Long studyId;
}
