package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.entity.Study;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class StudyResponseDto {
    private Long studyId;

    public StudyResponseDto(Study saved) {
        this.studyId  = saved.getStudyId();
    }
}
