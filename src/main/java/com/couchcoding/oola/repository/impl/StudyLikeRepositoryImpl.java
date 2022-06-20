package com.couchcoding.oola.repository.impl;

import com.couchcoding.oola.entity.StudyLike;
import com.couchcoding.oola.repository.StudyLikeRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.couchcoding.oola.entity.QStudyLike.*;

@Repository
public class StudyLikeRepositoryImpl extends QuerydslRepositorySupport implements StudyLikeRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public StudyLikeRepositoryImpl() {
        super(StudyLike.class);
    }

    @Override
    public List<StudyLike> findAllByLikeStatusAndUid(Boolean likeStatus, String uid) {
       List<StudyLike> studyLikes  = queryFactory.selectFrom(studyLike)
                                .where(eqLikeStatus(likeStatus), eqUid(uid)).fetch();
       return studyLikes;
    }

    private BooleanExpression eqLikeStatus(Boolean likeStatus) {
        if (likeStatus.equals(null) || likeStatus == null) {
            return null;
        }
        return studyLike.likeStatus.eq(likeStatus);
    }

    private BooleanExpression eqUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        return studyLike.member.uid.eq(uid);
    }
}
