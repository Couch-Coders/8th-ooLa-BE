package com.couchcoding.oola.dto.studylikes.response;

import com.couchcoding.oola.entity.Study;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyLikeStatus {

    private Long studyId;
    private String studyType;
    private String studyName;
    private LocalDateTime startDate;
    private int participants;
    private int currentParticipants;
    private Boolean status;

    public StudyLikeStatus(Study study, Boolean status) {
        this.studyId = study.getStudyId();
        this.studyType = study.getStudyType();
        this.studyName = study.getStudyName();
        this.startDate = study.getStartDate();
        this.participants = study.getParticipants();
        this.currentParticipants = study.getCurrentParticipants();
        this.status = status;
    }
}
