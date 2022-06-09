package com.couchcoding.oola.entity;

import com.couchcoding.oola.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "study_members")
public class StudyMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyId")
    private Study study;
    private String role;

    @Builder
    public StudyMember(Member member, Study study, String role) {
        this.member = member;
        this.study = study;
        this.role = role;
    }
}