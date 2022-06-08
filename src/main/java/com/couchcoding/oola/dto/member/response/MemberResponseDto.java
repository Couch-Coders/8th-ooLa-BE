package com.couchcoding.oola.dto.member.response;

import com.couchcoding.oola.entity.Member;

import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
public class MemberResponseDto {
    private String uid;
    private String displayName;
    private String email;
    private String blogUrl;
    private String githubUrl;
    private String photoUrl;
    private String status;

    public MemberResponseDto(Member member , String message) {
        this.uid = member.getUid();
        this.displayName = member.getDisplayName();
        this.email = member.getEmail();
        this.blogUrl = member.getBlogUrl();
        this.githubUrl = member.getGithubUrl();
        this.photoUrl = member.getPhotoUrl();
        this.status = message;
    }
}