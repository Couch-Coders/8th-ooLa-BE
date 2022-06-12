package com.couchcoding.oola.dto.mystudy.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyStudyCreationResponseDto {

    private String studyType;
    private String studyName;
    private LocalDateTime startDate;
    private Long participants;
    private Long currentParticipants;
}
