package com.couchcoding.oola.dto.studylikes.response;

import com.couchcoding.oola.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudyLikeListResponseDto {

    private Study study;
    private Boolean status;




}
