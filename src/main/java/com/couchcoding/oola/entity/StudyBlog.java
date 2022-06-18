package com.couchcoding.oola.entity;

import com.couchcoding.oola.dto.studyblogs.request.StudyBlogRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "study_blog")
public class StudyBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyBlogId;

    @Column(name = "comment")
    @NotBlank(message = "comment는 필수 값입니다")
    private String comment;

    @Column(name = "share_link")
    @NotBlank(message = "shareLink는 필수 값입니다")
    private String shareLink;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    public StudyBlog(StudyBlogRequestDto studyBlogRequestDto, Member member, Study study) {
        this.comment = studyBlogRequestDto.getComment();
        this.shareLink = studyBlogRequestDto.getShareLink();
        this.study = study;
        this.member = member;
    }
}
