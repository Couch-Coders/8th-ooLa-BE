package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Member;

public interface MemberRepositoryCustom {

    Member findByUid(String uid);
}
