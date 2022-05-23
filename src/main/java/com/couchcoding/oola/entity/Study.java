package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String studyType;

    @Column(name = "name")
    private String studyName;

    @Column(name = "timezone")
    private String timeZone;

    @Column(name = "participants")
    private int participants;

    @Column(name = "current_participants")
    private int currentParticipants;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "open_chat_url")
    private String openChatUrl;

    @Column(name = "introduce")
    private String studyIntroduce;

    @Column(name = "goal")
    private String studyGoal;

    @Column(name = "status")
    private String status;

    @Column(name = "joinStatus")
    private String joinStatus;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "count")
    private Long likeCount; // 프론트에서 스터디별 좋아요 개수를 구해달라는 요청이 있어 추가하였습니다.
}
