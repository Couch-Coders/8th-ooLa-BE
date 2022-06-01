package com.couchcoding.oola.entity;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@Table(name = "study")
public class Study extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers = new ArrayList<>();

    @Column(name = "type")
    @NotBlank(message = "studyType은 필수 값입니다")
    private String studyType;

    @Column(name = "name")
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

    @Column(name = "start_date")
    @NotNull(message = "startDate은 필수 값입니다")
    private Date startDate;

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

    @Column(name = "end_date")
    @NotNull(message = "openChatUrl은 필수 값입니다")
    private Date endDate;

    @Column(name = "count")
    private Long likeCount;

    @Builder
    public Study(Long studyId, @Null List<StudyMember> studyMembers, @NotBlank(message = "studyType은 필수 값입니다") String studyType, @NotBlank(message = "studyName은 필수 값입니다") String studyName, @NotBlank(message = "studydays는 필수 값입니다") String studyDays, @NotBlank(message = "timeZone은 필수 값입니다") String timeZone, @NotBlank(message = "participants은 필수 값입니다") int participants, int currentParticipants, @NotBlank(message = "startDate은 필수 값입니다") Date startDate, @NotBlank(message = "openChatUrl은 필수 값입니다") String openChatUrl, @NotBlank(message = "studyIntroduce은 필수 값입니다") String studyIntroduce, @NotBlank(message = "studyGoal은 필수 값입니다") String studyGoal, String status, String joinStatus,  @NotBlank(message = "openChatUrl은 필수 값입니다") Date endDate, Long likeCount) {
        this.studyId = studyId;
        this.studyMembers = studyMembers;
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
    }

    public Study update(Long studyId,StudyRequestDto studyRequestDto) {
         Study study = Study.builder()
                 .studyId(studyId)
                 .studyType(studyRequestDto.getStudyType())
                 .studyName(studyRequestDto.getStudyName())
                 .studyDays(studyRequestDto.getStudyDays())
                 .timeZone(studyRequestDto.getTimeZone())
                 .participants(studyRequestDto.getParticipants())
                 .startDate(studyRequestDto.getStartDate())
                 .endDate(studyRequestDto.getEndDate())
                 .openChatUrl(studyRequestDto.getOpenChatUrl())
                 .studyGoal(studyRequestDto.getStudyGoal())
                 .status(studyRequestDto.getStatus())
                 .joinStatus(studyRequestDto.getJoinStatus())
                 .studyIntroduce(studyRequestDto.getStudyIntroduce())
                 .build();
        return study;
    }

    public Study completeUpdate(Long studyId, String completeStatus, StudyRequestDto studyRequestDto) {

        Study study = Study.builder()
                .studyId(studyId)
                .studyType(studyRequestDto.getStudyType())
                .studyName(studyRequestDto.getStudyName())
                .studyDays(studyRequestDto.getStudyDays())
                .timeZone(studyRequestDto.getTimeZone())
                .participants(studyRequestDto.getParticipants())
                .startDate(studyRequestDto.getStartDate())
                .endDate(studyRequestDto.getEndDate())
                .openChatUrl(studyRequestDto.getOpenChatUrl())
                .studyGoal(studyRequestDto.getStudyGoal())
                .status(completeStatus)
                .joinStatus(studyRequestDto.getJoinStatus())
                .studyIntroduce(studyRequestDto.getStudyIntroduce())
                .build();
        return study;
    }

    @Override
    public String toString() {
        return "Study{" +
                "studyId=" + studyId +
                ", studyMembers=" + studyMembers +
                ", studyType='" + studyType + '\'' +
                ", studyName='" + studyName + '\'' +
                ", studyDays='" + studyDays + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", participants=" + participants +
                ", currentParticipants=" + currentParticipants +
                ", startDate=" + startDate +
                ", openChatUrl='" + openChatUrl + '\'' +
                ", studyIntroduce='" + studyIntroduce + '\'' +
                ", studyGoal='" + studyGoal + '\'' +
                ", status='" + status + '\'' +
                ", joinStatus='" + joinStatus + '\'' +
                ", endDate=" + endDate +
                ", likeCount=" + likeCount +
                '}';
    }
}
