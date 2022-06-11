package com.couchcoding.oola.dto.member.response;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.entity.Member;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberProfileResponseDto {
    private String uid;
    private String techStack;
    private String introduce;
    private String nickName;
    private String photoUrl;
    private String githubUrl;
    private String blogUrl;
    private String email;
    private String displayName;

    public MemberProfileResponseDto(String uid, String techStack, String introduce, String nickName, String photoUrl, String githubUrl, String blogUrl, String email, String displayName) {
        this.uid = uid;
        this.techStack = techStack;
        this.introduce = introduce;
        this.nickName = nickName;
        this.photoUrl = photoUrl;
        this.githubUrl = githubUrl;
        this.blogUrl = blogUrl;
        this.email = email;
        this.displayName = displayName;
    }

    public MemberProfileResponseDto profileUpdate(MemberSaveRequestDto memberSaveRequestDto) {
            this.uid = memberSaveRequestDto.getUid();
            this.techStack = memberSaveRequestDto.getTechStack().toString();
            this.introduce = memberSaveRequestDto.getIntroduce();
            this.nickName = memberSaveRequestDto.getNickName();
            this.photoUrl = memberSaveRequestDto.getPhotoUrl();
            this.githubUrl = memberSaveRequestDto.getGithubUrl();
            this.blogUrl = memberSaveRequestDto.getBlogUrl();
            this.email = memberSaveRequestDto.getEmail();
            this.displayName = memberSaveRequestDto.getDisplayName();
        return this;
    }


    public Member toEntity(MemberProfileResponseDto result) {
        return new Member(result.getUid(), result.getTechStack(),result.getIntroduce(),
                result.getNickName(), result.getPhotoUrl(), result.getGithubUrl() , result.getBlogUrl(), result.getEmail(), result.getDisplayName());
    }
}