package com.couchcoding.oola.dto.studyblogs.response;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.StudyBlog;
import com.couchcoding.oola.entity.StudyMember;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class StudyBlogListResponseDto {
    private List<StudyMember> studyMembers = new ArrayList<>();
    private List<StudyBlog> studyBlogs = new ArrayList<>();
    public StudyBlogListResponseDto(List<StudyMember> studyMembers , List<StudyBlog> studyBlogs) {
        this.studyMembers = studyMembers;
        this.studyBlogs = studyBlogs;
    }
}
