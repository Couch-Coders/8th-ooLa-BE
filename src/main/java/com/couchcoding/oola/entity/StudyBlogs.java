package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "study_blogs")
public class StudyBlogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "share_link")
    private String shareLink;

    @Column(name = "study_id")
    private Long studyId;

    @OneToMany(mappedBy = "studyBlogs")
    private List<Member> members = new ArrayList<>();
}
