package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "study_blog")
public class StudyBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "share_link")
    private String shareLink;

    @Column(name = "study_id")
    private Long studyId;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;
}
