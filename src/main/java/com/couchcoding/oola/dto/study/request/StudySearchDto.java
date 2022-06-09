package com.couchcoding.oola.dto.study.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class StudySearchDto {

    private String studyType;
    private String studyDays;
    private String timeZone;
    private String status;
}
