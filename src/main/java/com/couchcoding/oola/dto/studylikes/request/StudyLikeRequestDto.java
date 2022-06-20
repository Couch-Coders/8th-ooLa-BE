package com.couchcoding.oola.dto.studylikes.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyLikeRequestDto {
    private Long id;
    private Long studyId;
    private boolean likeStatus;

    public StudyLikeRequestDto(Long studyId, boolean likeStatus) {
        this.studyId = studyId;
        this.likeStatus = likeStatus;
    }
}
