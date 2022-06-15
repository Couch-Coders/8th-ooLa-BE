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

    private Study study;

    private List<StudyMember> studyMembers;

    public StudyResponseDetailDto toDto(Study study ) {
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto.study = study;
        return  studyResponseDetailDto;
    }

    public StudyResponseDetailDto(List<StudyMember> studyMembers) {
        this.studyMembers = studyMembers;
    }

    public StudyResponseDetailDto(Study study, List<StudyMember> studyMembers) {
        this.studyId = study.getId();
        this.studyType = study.getStudyType();
        this.studyDays = study.getStudyDays();
        this.timeZone = study.getTimeZone();
        this.participants = study.getParticipants();
        this.currentParticipants = study.getCurrentParticipants();
        this.startDate = study.getStartDate();
        this.openChatUrl = study.getOpenChatUrl();
        this.studyIntroduce = study.getStudyIntroduce();
        this.studyGoal = study.getStudyGoal();
        this.status = study.getStatus();
//        this.joinStatus = study.getJoinStatus();
//        this.likeStatus = study.getLikeStatus();
        this.studyName = study.getStudyName();
        this.endDate = study.getEndDate();
        this.studyMembers = studyMembers;

    }
}