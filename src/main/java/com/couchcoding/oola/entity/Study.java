package com.couchcoding.oola.entity;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.entity.base.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "study")
public class Study extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    @Column(name = "study_type")
    @NotBlank(message = "studyType은 필수 값입니다")
    private String studyType;

    @Column(name = "study_name")
    @NotBlank(message = "studyName은 필수 값입니다")
    private String studyName;

    @Column(name = "studydays")
    @NotBlank(message = "studydays는 필수 값입니다")
    private String studyDays;

    @Column(name = "timezone")
    @NotBlank(message = "timeZone은 필수 값입니다")
    private String timeZone;

    @Column(name = "participants")
    @NotNull(message = "participants은 필수 값입니다")
    private int participants;

    @Column(name = "current_participants")
    private int currentParticipants;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_date")
    @NotNull(message = "startDate은 필수 값입니다")
    private LocalDateTime startDate;

    @Column(name = "open_chat_url")
    @NotBlank(message = "openChatUrl은 필수 값입니다")
    private String openChatUrl;

    @Column(name = "introduce")
    @NotBlank(message = "studyIntroduce은 필수 값입니다")
    private String studyIntroduce;

    @Column(name = "goal")
    @NotBlank(message = "studyGoal은 필수 값입니다")
    private String studyGoal;

    @Column(name = "status")
    private String status;

    @Column(name = "joinStatus")
    private String joinStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_date")
    @NotNull(message = "openChatUrl은 필수 값입니다")
    private LocalDateTime endDate;

    @Column(name = "count")
    private Long likeCount;

    @Column(name = "create_uid")
    private String createUid;

    @Column(name = "like_status")
    private Boolean likeStatus; // 한명이라도 스터디 관심을 눌렀다면 true
    
    @Column(name = "participant_status")
    private Boolean participantStatus; // 스터디 상태가 완료인 경우 참여신청 불가 , 스터디 상태가 진행중인 경우 참여신청 불가

    @Builder
    public Study(Long studyId, @NotBlank(message = "studyType은 필수 값입니다") String studyType, @NotBlank(message = "studyName은 필수 값입니다") String studyName, @NotBlank(message = "studydays는 필수 값입니다") String studyDays, @NotBlank(message = "timeZone은 필수 값입니다") String timeZone, @NotNull(message = "participants은 필수 값입니다") int participants, int currentParticipants, @NotNull(message = "startDate은 필수 값입니다") LocalDateTime startDate, @NotBlank(message = "openChatUrl은 필수 값입니다") String openChatUrl, @NotBlank(message = "studyIntroduce은 필수 값입니다") String studyIntroduce, @NotBlank(message = "studyGoal은 필수 값입니다") String studyGoal, String status, String joinStatus, @NotNull(message = "openChatUrl은 필수 값입니다") LocalDateTime endDate, Long likeCount, String createUid, Boolean likeStatus) {
        this.studyId = studyId;
        this.studyType = studyType;
        this.studyName = studyName;
        this.studyDays = studyDays;
        this.timeZone = timeZone;
        this.participants = participants;
        this.currentParticipants = currentParticipants;
        this.startDate = startDate;
        this.openChatUrl = openChatUrl;
        this.studyIntroduce = studyIntroduce;
        this.studyGoal = studyGoal;
        this.status = status;
        this.joinStatus = joinStatus;
        this.endDate = endDate;
        this.likeCount = likeCount;
        this.createUid = createUid;
        this.likeStatus = likeStatus;
    }

    // 스터디 수정
    public Study update(Long studyId, StudyRequestDto studyRequestDto, String uid) {
        this.studyType = studyRequestDto.getStudyType();
        this.studyName = studyRequestDto.getStudyName();
        this.studyDays = studyRequestDto.getStudyDays();
        this.timeZone = studyRequestDto.getTimeZone();
        this.participants = studyRequestDto.getParticipants();
        this.startDate = studyRequestDto.getStartDate();
        this.endDate = studyRequestDto.getEndDate();
        this.openChatUrl = studyRequestDto.getOpenChatUrl();
        this.studyIntroduce = studyRequestDto.getStudyIntroduce();
        this.studyGoal = studyRequestDto.getStudyGoal();
        this.status = studyRequestDto.getStatus();
        this.joinStatus = studyRequestDto.getJoinStatus();
        this.likeStatus = studyRequestDto.getLikeStatus();
        this.currentParticipants = studyRequestDto.getCurrentParticipants();
        this.studyId = studyId;
        this.createUid = uid;
        return this;
    }

    // 스터디 완료시 상태 수정
    public Study updateCompleteStatus(String completeStatus) {
        this.status = completeStatus;
        return this;
    }

    // 스터디 신청시 현재 참여인원 수정
    public Study updateCurrentParticipants(int updateParticipants) {
        this.currentParticipants = updateParticipants;
        return this;
    }
}