package com.couchcoding.oola.dto.studycomments.request;

import com.couchcoding.oola.entity.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyCommentRequestDto {
    private String content;
}
