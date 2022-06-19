package com.couchcoding.oola.dto.studylikes.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyHateRequestDto {
    private Long id;
    private Long studyId;
    private boolean likeStatus;
}
