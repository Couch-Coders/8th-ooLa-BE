package com.couchcoding.oola.repository.impl;

import com.couchcoding.oola.entity.QStudyBlog;
import com.couchcoding.oola.entity.StudyBlog;
import com.couchcoding.oola.repository.StudyBlogRespositoryCustom;
import com.couchcoding.oola.repository.StudyMemberRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.couchcoding.oola.entity.QStudyBlog.studyBlog;
import static com.couchcoding.oola.entity.QStudyMember.studyMember;

@Repository
public class StudyBlogRepositoryCustomImpl extends QuerydslRepositorySupport implements StudyBlogRespositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public StudyBlogRepositoryCustomImpl() {
        super(QStudyBlog.class);
    }

    @Override
    public List<StudyBlog> findAllByStudyId(Long studyId) {
       List<StudyBlog> studyBlogs  = queryFactory.selectFrom(studyBlog)
                            .where(eqStudyId(studyId)).fetch();
        return studyBlogs;
    }

    private BooleanExpression eqStudyId(Long studyId) {
        if (studyId == null) {
            return null;
        }
        return studyBlog.study.studyId.eq(studyId);
    }
}
