package com.couchcoding.oola.dto.studylikes.response;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class StudyLikeResponseDto {

    private Long id;
    private String uid;
    private Long studyId;
    private boolean likeStatus;
}
