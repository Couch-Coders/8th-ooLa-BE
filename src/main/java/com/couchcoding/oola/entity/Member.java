package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    private String uid;

    @Column(name = "display_name")
    @NotBlank(message = "displayName은 필수 값입니다")
    private String displayName;

    @Column(name = "email")
    @NotBlank(message = "email은 필수 값입니다")
    private String email;

    @Column(name = "blog_url")
    private String blogUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "photo_url")
    @NotBlank(message = "photoUrl은 필수 값입니다")
    private String photoUrl;

    public Member(@NotBlank(message = "displayName은 필수 값입니다") String displayName, @NotBlank(message = "email은 필수 값입니다") String email, String blogUrl, String githubUrl, @NotBlank(message = "photoUrl은 필수 값입니다") String photoUrl) {
        this.displayName = displayName;
        this.email = email;
        this.blogUrl = blogUrl;
        this.githubUrl = githubUrl;
        this.photoUrl = photoUrl;
    }
}
