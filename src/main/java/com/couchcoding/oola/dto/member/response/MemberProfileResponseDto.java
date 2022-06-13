package com.couchcoding.oola.dto.member.response;

import com.couchcoding.oola.entity.Member;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberProfileResponseDto {
    private String uid;
    private List<String> techStack;
    private String introduce;
    private String nickName;
    private String photoUrl;
    private String githubUrl;
    private String blogUrl;
    private String email;

    public MemberProfileResponseDto(Member member) {
        this.uid = member.getUid();
        this.techStack = member.getTechStack();
        this.introduce = member.getIntroduce();
        this.nickName = member.getNickName();
        this.photoUrl = member.getPhotoUrl();
        this.githubUrl = member.getGithubUrl();
        this.blogUrl = member.getBlogUrl();
        this.email = member.getEmail();
    }

}