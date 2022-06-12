package com.couchcoding.oola.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponseDto {
    private String uid;
    private String displayName;
    private String email;
    private String blogUrl;
    private String githubUrl;
    private String photoUrl;
}