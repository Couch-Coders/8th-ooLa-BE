package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.entity.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyResponseDto {
    private Long studyId;

    public StudyResponseDto(Study saved) {
        this.studyId  = saved.getStudyId();
    }
}
