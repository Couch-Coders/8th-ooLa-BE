package com.couchcoding.oola.dto.studyblogs.response;

import com.couchcoding.oola.entity.Member;
import lombok.ToString;

@ToString
public class StudyBlogListResponseDto {
    private Long studyBlogId;
    private String comment;
    private String shareLink;
    private String uid;
    private String nickName;
    private String photoUrl;
    private String role;

    public StudyBlogListResponseDto(Long studyBlogId,String comment, String shareLink, String role, Member member) {
        this.studyBlogId = studyBlogId;
        this.comment = comment;
        this.shareLink = shareLink;
        this.role = role;
        this.uid = member.getUid();
        this.nickName = member.getNickName();
        this.photoUrl = member.getPhotoUrl();
    }
}
