package com.couchcoding.oola.dto.member.response;

import com.couchcoding.oola.dto.member.request.MemberSaveRequestDto;
import com.couchcoding.oola.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberProfileResponseDto {
    private Member member;

    public Member profileUpdate(String uid, MemberSaveRequestDto memberSaveRequestDto) {
        Member member = Member.builder()
                .uid(uid)
                .techStack(memberSaveRequestDto.getTechStack().toString())
                .introduce(memberSaveRequestDto.getIntroduce())
                .nickName(memberSaveRequestDto.getNickName())
                .photoUrl(memberSaveRequestDto.getPhotoUrl())
                .githubUrl(memberSaveRequestDto.getGithubUrl())
                .blogUrl(memberSaveRequestDto.getBlogUrl())
                .email(memberSaveRequestDto.getEmail())
                .displayName(memberSaveRequestDto.getDisplayName())
                .build();

        return member;
    }
}