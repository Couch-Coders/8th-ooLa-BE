package com.couchcoding.oola.dto.member.request;

import com.couchcoding.oola.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String uid;
    private String displayName;
    @Email
    private String email;
    private String blogUrl;
    private String githubUrl;
    private String photoUrl;
    private String nickName;
    private String introduce;
    private List<String> techStack;

    @Builder
    public MemberSaveRequestDto(String uid, String displayName, @Email String email, String blogUrl, String githubUrl, String photoUrl, String nickName, String introduce, List<String> techStack) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.blogUrl = blogUrl;
        this.githubUrl = githubUrl;
        this.photoUrl = photoUrl;
        this.nickName = nickName;
        this.introduce = introduce;
        this.techStack = techStack;
    }

    public Member toEntity(MemberSaveRequestDto dto , List<String> list) {
        Member member = Member.builder()
                .uid(dto.getUid())
                .displayName(dto.getDisplayName())
                .email(dto.getEmail())
                .blogUrl(dto.getBlogUrl())
                .githubUrl(dto.getGithubUrl())
                .photoUrl(dto.getPhotoUrl())
                .nickName(dto.getNickName())
                .introduce(dto.getIntroduce())
                .techStack(list)
                .build();
        return member;
    }
}