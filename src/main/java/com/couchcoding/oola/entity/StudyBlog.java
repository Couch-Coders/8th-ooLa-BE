package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class StudyBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    @NotBlank(message = "comment는 필수 값입니다")
    private String comment;

    @Column(name = "share_link")
    @NotBlank(message = "shareLink는 필수 값입니다")
    private String shareLink;

    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;
}
