package com.couchcoding.oola.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "study_likes")
public class StudyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;

    @Column(name = "like_status")
    private boolean likeStatus;

    public StudyLike(Member member, Study study, boolean yesLike) {
        this.member = member;
        this.study = study;
        this.likeStatus = yesLike;
    }
}
