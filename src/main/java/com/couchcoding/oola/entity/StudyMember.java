package com.couchcoding.oola.entity;

import com.couchcoding.oola.entity.base.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "study_members")
public class StudyMember extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyMemberId;

    @Column(name = "uid", insertable = false, updatable = false)
    private String uid;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @Column(name = "studyId",insertable = false, updatable = false)
    private Long studyId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyId")
    private Study study;
    private String role;

    @Builder
    public StudyMember(Member member, Long studyId, Study study, String role) {
        this.member = member;
        this.studyId = studyId;
        this.study = study;
        this.role = role;
    }

    // 스터디 수정시 리더 판단
    public Boolean isLeader(String uid) {
        if (this.getMember().getUid().equals(uid)) {
            return true;
        }
        return false;
    }
}
