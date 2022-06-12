package com.couchcoding.oola.dto.member.response;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
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
}