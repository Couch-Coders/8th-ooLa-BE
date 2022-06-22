package com.couchcoding.oola.dto.studycomments.response;

import com.couchcoding.oola.entity.Comment;
import com.couchcoding.oola.entity.Member;
import lombok.Getter;

@Getter
public class StudyCommentMemberResponseDto {
    private String role;
    private String uid;
    private String nickName;
    private String photoUrl;

    public StudyCommentMemberResponseDto(String role, Member member) {
        this.role = role;
        this.uid = member.getUid();
        this.nickName = member.getNickName();
        this.photoUrl = member.getPhotoUrl();
    }


}
