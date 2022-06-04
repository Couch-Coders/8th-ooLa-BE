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

@Repository
@Slf4j
public class StudyRepositoryImpl extends QuerydslRepositorySupport implements StudyRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public StudyRepositoryImpl() {
        super(Study.class);
    }

    @Override
    public List<Study> findBySearchOption( String studyType, String studyDays,String timeZone, String status) {
        List<Study> query = (List<Study>) queryFactory.selectFrom(study)
                .where(eqStudyType(studyType), eqStudyDays(studyDays), eqTimeZone(timeZone), eqStatus(status)).fetch();
        log.info("query1: {}" +  query.toString());
        return query;
    }

    @Override
    public List<Study> findBySearchOption(String studyType, String studyDays, String timeZone) {
        List<Study> query = (List<Study>) queryFactory.selectFrom(study)
                .where(eqStudyType(studyType), eqStudyDays(studyDays), eqTimeZone(timeZone)).fetch();
        log.info("query2: {}" +  query.toString());
        return query;
    }

    @Override
    public List<Study> findBySearchOption( String studyType, String studyDays) {
        List<Study> query = (List<Study>) queryFactory.selectFrom(study)
                .where(eqStudyType(studyType), eqStudyDays(studyDays)).fetch();
        log.info("query3: {}" +  query.toString());
        return query;
    }

    @Override
    public List<Study> findBySearchOption( String studyType) {
        List<Study> query = (List<Study>) queryFactory.selectFrom(study)
                .where(eqStudyType(studyType)).fetch();
        log.info("query4: {}" +  query.toString());
        return query;
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
}
