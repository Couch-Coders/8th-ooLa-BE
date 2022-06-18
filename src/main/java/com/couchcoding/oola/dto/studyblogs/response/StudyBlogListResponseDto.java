package com.couchcoding.oola.dto.studyblogs.response;

import com.couchcoding.oola.entity.StudyBlog;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyBlogListResponseDto {
    private List<StudyBlogMemberResponseDto> studyBlogMemberResponseDtos;
    private List<StudyBlog> studyBlogs;

    public StudyBlogListResponseDto(List<StudyBlogMemberResponseDto> studyBlogMemberResponseDtos , List<StudyBlog> studyBlogs) {
        this.studyBlogMemberResponseDtos = studyBlogMemberResponseDtos;
        this.studyBlogs = studyBlogs;
    }
}
