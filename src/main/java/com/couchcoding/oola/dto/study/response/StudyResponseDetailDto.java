package com.couchcoding.oola.dto.study.response;

import com.couchcoding.oola.entity.Study;
import lombok.*;

import java.time.LocalDateTime;

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

    public StudyResponseDetailDto toDto(Study study) {
        return  new StudyResponseDetailDto(study);
    }

    public StudyResponseDetailDto(Study study) {
        this.studyId = study.getStudyId();
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
        this.studyName = study.getStudyName();
        this.endDate = study.getEndDate();
    }
}