package com.couchcoding.oola.dto.member.response;

import com.couchcoding.oola.entity.Member;

import lombok.Getter;

import java.util.Optional;

@Getter
public class MemberResponseDto {
    private String uid;
    private String displayName;
    private String email;
    private String blogUrl;
    private String githubUrl;
    private String photoUrl;

    public MemberResponseDto(Member member) {
        this.uid = member.getUid();
        this.displayName = member.getDisplayName();
        this.email = member.getEmail();
        this.blogUrl = member.getBlogUrl();
        this.githubUrl = member.getGithubUrl();
        this.photoUrl = member.getPhotoUrl();
    }
}
