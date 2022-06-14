package com.couchcoding.oola.repository.custom;

import com.couchcoding.oola.entity.Member;

public interface MemberRepositoryCustom {

    Member findByUid(String uid);
}
