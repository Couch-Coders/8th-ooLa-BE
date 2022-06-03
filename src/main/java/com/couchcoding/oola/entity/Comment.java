package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @Column(name = "content")
    @NotBlank(message = "content는 필수 값입니다")
    private String content;

    @Column(name = "parent_no")
    private Long parentNo;

    @Column(name = "date")
    private Date insertDate;
}
