package com.couchcoding.oola.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "comment")
    private List<Member> members = new ArrayList<>();

    @Column(name = "comment")
    private String comment;

    @Column(name = "parent_no")
    private Long parentNo;

    @Column(name = "date")
    private Date insertDate;
}
