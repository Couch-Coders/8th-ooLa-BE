package com.couchcoding.oola.entity;

import com.couchcoding.oola.dto.studycomments.request.CommentRequestDto;
import com.couchcoding.oola.dto.studycomments.request.StudyCommentRequestDto;
import com.couchcoding.oola.entity.base.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyId")
    private Study study;

    @Column(name = "content")
    @NotBlank(message = "content는 필수 값입니다")
    private String content;
//
//    @Column(name = "parent_no")
//    private Long parentNo;

    public Comment(CommentRequestDto commentRequestDto, Member member , Study study) {
        this.id = commentRequestDto.getId();
        this.content = commentRequestDto.getContent();
        this.member = member;
        this.study = study;

    }

    public Comment(Member member, Study study, StudyCommentRequestDto studyCommentRequestDto) {
        this.member = member;
        this.study = study;
        this.content = studyCommentRequestDto.getContent();
    }

    public Comment update(Comment entity , CommentRequestDto commentRequestDto) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.study = entity.getStudy();
        this.content = commentRequestDto.getContent();
        return this;
    }
}
