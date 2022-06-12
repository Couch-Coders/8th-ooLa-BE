package com.couchcoding.oola.repository.impl;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.QStudyMember;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyMemberRepositoryCustom;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.couchcoding.oola.entity.QStudyMember.*;

@Slf4j
@Repository
public class StudyMemberRepositoryImpl extends QuerydslRepositorySupport implements StudyMemberRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public StudyMemberRepositoryImpl() {
        super(StudyMember.class);
    }

    @Override
    public List<StudyMember> findByStudyId(Long studyId) {
        List<StudyMember> studyMembers = queryFactory.selectFrom(studyMember)
        .where(eqStudyId(studyId)).fetch();
        return studyMembers;
    }

    @Override
    public List<StudyMember> findAllByUidAndRole(Long uid, String role) {
        List<StudyMember> studyMembers = queryFactory.selectFrom(studyMember)
                .where(eqUid(uid), eqRole(role)).fetch();

        log.info("studyMember: {} ", studyMembers.toString());
        log.info("studyMember.get(0) : {}", studyMembers.get(0).getStudy().toString());
        return studyMembers;
    }

    @Override
    public List<StudyMember> findAllByUidAndRoleAndStatus(Long uid, String role, String status) {
        List<StudyMember> studyMembers = queryFactory.selectFrom(studyMember)
                .where(eqUid(uid), eqRole(role), eqStatus(status))
                .fetch();

        log.info("studyMember: {} ", studyMembers.toString());
        return studyMembers;
    }

    private BooleanExpression eqStatus(String status) {
        if (status.equals(null) || status == null) {
            return null;
        }
        return studyMember.study.status.eq(status);
    }

    private BooleanExpression eqRole(String role) {
        if (role.equals(null) || role == null) {
            return null;
        }
        return studyMember.role.eq(role);
    }

    private BooleanExpression eqStudyId(Long studyId) {
        if (studyId == null) {
            return null;
        }
        return studyMember.studyId.eq(studyId);
    }

    private BooleanExpression eqUid(Long uid) {
       if (uid == null) {
           return null;
       }
       return studyMember.uid.eq(uid);
    }
}