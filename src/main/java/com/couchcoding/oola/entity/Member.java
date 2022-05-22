package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    private String uid;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberLanguage> memberLanguages = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<StudyLike> studyLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<StudyBlog> studyBlogs = new ArrayList<>();

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email")
    private String email;

    @Column(name = "blog_url")
    private String blogUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "photo_url")
    private String photoUrl;
}
