package com.couchcoding.oola.dto.studyblogs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
@AllArgsConstructor
public class StudyBlogMemberResponseDto {
    private String uid;
    private String blogUrl;
    private String githubUrl;
    private List<String> techStack;
    private String role;
}
