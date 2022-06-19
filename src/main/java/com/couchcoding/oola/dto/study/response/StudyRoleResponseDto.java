package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudyRoleResponseDto {

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
    private LocalDateTime endDate;
    private String role;
    private boolean likeStatus;

    public StudyRoleResponseDto(Study study, String role , boolean likeStatus) {
        this.studyId = study.getStudyId();
        this.studyType = study.getStudyType();
        this.studyName = study.getStudyName();
        this.timeZone = study.getTimeZone();
        this.participants  = study.getParticipants();
        this.currentParticipants = study.getCurrentParticipants();
        this.startDate  = study.getStartDate();
        this.openChatUrl = study.getOpenChatUrl();
        this.studyIntroduce = study.getStudyIntroduce();
        this.studyGoal = study.getStudyGoal();
        this.status = study.getStatus();
        this.endDate = study.getEndDate();
        this.studyDays = study.getStudyDays();
        this.role = role;
        this.likeStatus = likeStatus;
    }



}
