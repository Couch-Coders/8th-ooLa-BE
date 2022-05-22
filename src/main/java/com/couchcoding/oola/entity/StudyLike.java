package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "study_likes")
public class StudyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "studyLike")
    private List<Member> members = new ArrayList<>();

    @Column(name = "studyid")
    private Long studyId;

    @Column(name = "like_status")
    private boolean likeStatus;


}
