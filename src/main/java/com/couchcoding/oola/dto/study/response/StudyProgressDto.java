package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudyProgressDto {
    private Study study;
}
