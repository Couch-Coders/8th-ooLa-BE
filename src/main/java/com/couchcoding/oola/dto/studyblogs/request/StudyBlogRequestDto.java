package com.couchcoding.oola.dto.studyblogs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyBlogRequestDto {
    private String comment;
    private String shareLink;
}
