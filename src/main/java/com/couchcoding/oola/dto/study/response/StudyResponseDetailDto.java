package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class StudyResponseDetailDto {

    private Long studyId;

    private String studyType;

    private String studyName;

    private String studyDays;

    private String timeZone;

    private int participants;

    private int currentParticipants;

    private LocalDateTime startDate;

    private String openChatUrl;

    private String studyIntroduce;

    private String studyGoal;

    private String status;

    private String joinStatus;

    private LocalDateTime endDate;

    private boolean likeStatus;

    public StudyResponseDetailDto toDto(Study study) {
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto.setStudyId(study.getStudyId());
        studyResponseDetailDto.setStudyType(study.getStudyType());
        studyResponseDetailDto.setStudyDays(study.getStudyDays());
        studyResponseDetailDto.setTimeZone(study.getTimeZone());
        studyResponseDetailDto.setParticipants(study.getParticipants());
        studyResponseDetailDto.setCurrentParticipants(study.getCurrentParticipants());
       studyResponseDetailDto.setStartDate(study.getStartDate());
       studyResponseDetailDto.setOpenChatUrl(study.getOpenChatUrl());
       studyResponseDetailDto.setStudyIntroduce(study.getStudyIntroduce());
       studyResponseDetailDto.setStudyGoal(study.getStudyGoal());
        studyResponseDetailDto.setStatus(study.getStatus());
        studyResponseDetailDto.setJoinStatus(study.getJoinStatus());
        studyResponseDetailDto.setLikeStatus(study.getLikeStatus());
        studyResponseDetailDto.setStudyName(study.getStudyName());
        studyResponseDetailDto.setEndDate(study.getEndDate());

        return studyResponseDetailDto;
    }
}