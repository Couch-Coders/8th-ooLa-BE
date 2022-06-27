package com.couchcoding.oola.repository.impl;

import com.couchcoding.oola.entity.Comment;
import com.couchcoding.oola.entity.QComment;
import com.couchcoding.oola.repository.StudyCommentRepositoryCustom;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StudyCommentRepositoryImpl extends QuerydslRepositorySupport implements StudyCommentRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;


    public StudyCommentRepositoryImpl() {
        super(QComment.class);
    }


    @Override
    public List<Comment> findAllByStudyId(Long studyId) {
        List<Comment> comments = queryFactory.selectFrom(QComment.comment)
                .where(eqStudyId(studyId))
                .orderBy(QComment.comment.createdDate.asc()).fetch();
        return comments;
    }

    private BooleanExpression eqStudyId(Long studyId) {
        if (studyId == null) {
            return null;
        }
        return QComment.comment.study.studyId.eq(studyId);
    }
}
