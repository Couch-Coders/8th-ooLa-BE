package com.couchcoding.oola.dto.studyblogs.response;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyBlog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyBlogResponseDto {
    private Long id;
    private  String comment;
    private String shareLink;
    private Study study;
    private Member member;

    public StudyBlogResponseDto(StudyBlog studyBlog) {
        this.id = studyBlog.getStudyBlogId();
        this.comment = studyBlog.getComment();
        this.shareLink = studyBlog.getShareLink();
        this.study = studyBlog.getStudy();
        this.member = studyBlog.getMember();
    }

}
