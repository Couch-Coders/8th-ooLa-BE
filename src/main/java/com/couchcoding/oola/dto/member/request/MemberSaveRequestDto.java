package com.couchcoding.oola.dto.member.request;

import com.couchcoding.oola.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String uid;
    private String displayName;
    private String email;
    private String blogUrl;
    private String githubUrl;
    private String photoUrl;

    @Builder
    public MemberSaveRequestDto(String uid, String displayName, String email, String blogUrl, String githubUrl, String photoUrl) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.blogUrl = blogUrl;
        this.githubUrl = githubUrl;
        this.photoUrl = photoUrl;
    }

    public Member toEntity(MemberSaveRequestDto dto) {
        Member member = Member.builder()
                .uid(dto.getUid())
                .displayName(dto.getDisplayName())
                .email(dto.getEmail())
                .blogUrl(dto.getBlogUrl())
                .githubUrl(dto.getGithubUrl())
                .photoUrl(dto.getPhotoUrl())
                .build();
        return member;
    }


}
