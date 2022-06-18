package com.couchcoding.oola.dto.study.request;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class StudyRequestDto {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "startDate은 필수 값입니다")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "endDate는 필수 값입니다")
    private LocalDateTime endDate;

    @NotBlank(message = "openChatUrl은 필수 값입니다")
    private String openChatUrl;

    @NotBlank(message = "studyIntroduce은 필수 값입니다")
    private String studyIntroduce;

    @NotBlank(message = "studyGoal은 필수 값입니다")
    private String studyGoal;

    private String status;

    private String createUid;

    private Integer currentParticipants;

    private Boolean likeStatus;

    private Boolean participantStatus;

    public Study toEntity(StudyRequestDto studyRequestDto , Member member) {
        return new Study(studyRequestDto, member);
    }
}
