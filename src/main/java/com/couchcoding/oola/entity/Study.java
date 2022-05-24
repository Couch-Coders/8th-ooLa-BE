package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "study")
public class Study {
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

    @Column(name = "timezone")
    @NotBlank(message = "timeZone은 필수 값입니다")
    private String timeZone;

    @Column(name = "participants")
    @NotBlank(message = "participants은 필수 값입니다")
    private int participants;

    @Column(name = "current_participants")
    private int currentParticipants;

    @Column(name = "start_date")
    @NotBlank(message = "startDate은 필수 값입니다")
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

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "end_date")
    @NotBlank(message = "openChatUrl은 필수 값입니다")
    private Date endDate;

    @Column(name = "count")
    private Long likeCount; // 프론트에서 스터디별 좋아요 개수를 구해달라는 요청이 있어 추가하였습니다.
}
