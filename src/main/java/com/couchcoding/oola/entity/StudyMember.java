package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "study_members")
public class StudyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;

    private String role;
    private Date applicationDate;

    public StudyMember(Member member, Study study, String role, Date applicationDate) {
        this.member = member;
        this.study = study;
        this.role = role;
        this.applicationDate = applicationDate;
    }
}
