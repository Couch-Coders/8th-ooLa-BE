package com.couchcoding.oola.repository.impl;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.QMember;
import com.couchcoding.oola.repository.MemberRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.couchcoding.oola.entity.QMember.*;


@Repository
public class JpaQueryMemberRepository extends QuerydslRepositorySupport implements MemberRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public JpaQueryMemberRepository() {
        super(Member.class);
    }

    @Override
    public Member findByUid(String uid) {
        return  queryFactory.selectFrom(QMember.member)
                .where(eqUid(uid)).fetchOne();
    }

    private BooleanExpression eqUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        return member.uid.eq(uid);
    }
}
