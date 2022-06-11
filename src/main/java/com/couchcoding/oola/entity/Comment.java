package com.couchcoding.oola.entity;

import com.couchcoding.oola.entity.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "studyId")
    private Study study;

    @Column(name = "content")
    @NotBlank(message = "content는 필수 값입니다")
    private String content;

    @Column(name = "parent_no")
    private Long parentNo;

    public Comment update(Comment entity) {
        this.parentNo = entity.getId();
        this.id = entity.getId();
        this.member = entity.getMember();
        this.study = entity.getStudy();
        this.content = entity.getContent();
        return this;
    }
}
