package com.couchcoding.oola.dto.study.request;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Slf4j
public class StudyRequestDto {

    // 로그인된 사용자의 uid를 저장하기 위함
    @Null
    private String memberUid;

    @NotBlank(message = "studyType은 필수 값입니다")
    private String studyType;

    @NotBlank(message = "studyName은 필수 값입니다")
    private String studyName;

    @NotBlank(message = "studydays는 필수 값입니다")
    private String studyDays;

    @NotBlank(message = "timeZone은 필수 값입니다")
    private String timeZone;

    @NotNull(message = "participants은 필수 값입니다")
    private int participants;

    @NotNull(message = "startDate은 필수 값입니다")
    private Date startDate;

    @NotNull(message = "openChatUrl은 필수 값입니다")
    private Date endDate;

    @NotBlank(message = "openChatUrl은 필수 값입니다")
    private String openChatUrl;

    @NotBlank(message = "studyIntroduce은 필수 값입니다")
    private String studyIntroduce;

    @NotBlank(message = "studyGoal은 필수 값입니다")
    private String studyGoal;

    private String status;

    private String joinStatus;

    private Integer currentParticipants;


    public void setMemberUid(String memberUid) {
        this.memberUid = memberUid;
    }

    @Builder
    public StudyRequestDto(@Null String memberUid, @NotBlank(message = "studyType은 필수 값입니다") String studyType, @NotBlank(message = "studyName은 필수 값입니다") String studyName, @NotBlank(message = "studydays는 필수 값입니다") String studyDays, @NotBlank(message = "timeZone은 필수 값입니다") String timeZone, @NotNull(message = "participants은 필수 값입니다") int participants, @NotNull(message = "startDate은 필수 값입니다") Date startDate, @NotNull(message = "openChatUrl은 필수 값입니다") Date endDate, @NotBlank(message = "openChatUrl은 필수 값입니다") String openChatUrl, @NotBlank(message = "studyIntroduce은 필수 값입니다") String studyIntroduce, @NotBlank(message = "studyGoal은 필수 값입니다") String studyGoal, String status, String joinStatus, Integer currentParticipants) {
        this.memberUid = memberUid;
        this.studyType = studyType;
        this.studyName = studyName;
        this.studyDays = studyDays;
        this.timeZone = timeZone;
        this.participants = participants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.openChatUrl = openChatUrl;
        this.studyIntroduce = studyIntroduce;
        this.studyGoal = studyGoal;
        this.status = status;
        this.joinStatus = joinStatus;
        this.currentParticipants = currentParticipants;
    }

    public Study toEntity( StudyRequestDto studyRequestDto) {
        Study study = Study.builder()
                .studyType(studyRequestDto.getStudyType())
                .studyName(studyRequestDto.getStudyName())
                .studyDays(studyRequestDto.getStudyDays())
                .timeZone(studyRequestDto.getTimeZone())
                .participants(studyRequestDto.getParticipants())
                .currentParticipants(0)
                .startDate(studyRequestDto.getStartDate())
                .openChatUrl(studyRequestDto.getOpenChatUrl())
                .studyIntroduce(studyRequestDto.getStudyIntroduce())
                .studyGoal(studyRequestDto.getStudyGoal())
                .status(studyRequestDto.getStatus())
                .joinStatus(studyRequestDto.getJoinStatus())
//                .createdDate(null)
                .endDate(studyRequestDto.getEndDate())
                .likeCount(0L)
                .currentParticipants(studyRequestDto.getCurrentParticipants())
                .build();

        return study;
    }
}
