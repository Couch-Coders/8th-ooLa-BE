package com.couchcoding.oola.dto.member.response;

import com.couchcoding.oola.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberLoginResponseDto {
    private String uid;
    private String email;
    private String blogUrl;
    private String githubUrl;
    private String photoUrl;
    private String nickName;

    public MemberLoginResponseDto(Member member) {
        this.uid = member.getUid();
        this.email = member.getEmail();
        this.blogUrl = member.getBlogUrl();
        this.githubUrl = member.getGithubUrl();
        this.photoUrl = member.getPhotoUrl();
        this.nickName = member.getNickName();
    }
}