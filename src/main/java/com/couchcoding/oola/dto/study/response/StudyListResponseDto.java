package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.dto.studylikes.response.StudyLikeListResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeStatus;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudyListResponseDto {

    private String uid;
    private List<StudyLikeResponseDto> studyLikeResponseDto;
    private Page<Study> studies;

}
