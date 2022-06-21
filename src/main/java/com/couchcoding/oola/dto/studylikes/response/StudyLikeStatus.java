package com.couchcoding.oola.dto.studylikes.response;

import com.couchcoding.oola.entity.Study;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyLikeStatus {

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
    private String createUid;
    private Boolean likeStatus;

    public StudyLikeStatus(Study study, Boolean status) {
        this.studyId = study.getStudyId();
        this.studyType = study.getStudyType();
        this.studyName = study.getStudyName();
        this.studyDays = study.getStudyDays();
        this.timeZone = study.getTimeZone();
        this.participants = study.getParticipants();
        this.currentParticipants = study.getCurrentParticipants();
        this.startDate = study.getStartDate();
        this.openChatUrl = study.getOpenChatUrl();
        this.studyIntroduce = study.getStudyIntroduce();
        this.studyGoal = study.getStudyGoal();
        this.status = study.getStatus();
        this.endDate = study.getEndDate();
        this.createUid = study.getCreateUid();
        this.likeStatus = status;

    }
}
