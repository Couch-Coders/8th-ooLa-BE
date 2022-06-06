package com.couchcoding.oola.repository.impl;

import com.couchcoding.oola.entity.QStudy;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.repository.StudyRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jdk.jfr.Registered;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.couchcoding.oola.entity.QStudy.*;


@Slf4j
@Repository
public class StudyRepositoryImpl extends QuerydslRepositorySupport implements StudyRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public StudyRepositoryImpl() {
        super(Study.class);
    }

    @Override
    public Page<Study> findBySearchOption(Pageable pageable, String studyType, String studyDays,String timeZone, String status, String studyName) {
        JPQLQuery<Study> query = queryFactory.selectFrom(study)
                .where(eqStudyType(studyType), eqStudyDays(studyDays), eqTimeZone(timeZone), eqStatus(status), eqStudyName(studyName));

        List<Study> studies = this.getQuerydsl().applyPagination(pageable,query).fetch();
        log.info("stuides: {}" +  studies.toString());
        return new PageImpl<Study>(studies, pageable, query.fetchCount());
    }

    private BooleanExpression eqStudyType(String studyType) {
        if (studyType == null || studyType.isEmpty()) {
            return null;
        }
        return study.studyType.eq(studyType);
    }

    private BooleanExpression eqStudyDays(String studyDays) {
        if (studyDays == null || studyDays.isEmpty()) {
            return null;
        }
        return study.studyDays.eq(studyDays);
    }

    private BooleanExpression eqTimeZone(String timeZone) {
        if (timeZone == null || timeZone.isEmpty()) {
            return null;
        }
        return study.timeZone.eq(timeZone);
    }

    private BooleanExpression eqStatus(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        return study.status.eq(status);
    }

    private BooleanExpression eqStudyName(String studyName) {
        if (studyName == null || studyName.isEmpty()) {
            return null;
        }
        return study.studyName.contains(studyName);
    }
}
