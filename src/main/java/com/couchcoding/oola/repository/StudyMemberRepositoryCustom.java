package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyMember;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface StudyMemberRepositoryCustom {

    List<StudyMember> findAllByStudyId(Long studyId);
    List<StudyMember> findAllByUidAndRoleAndStatus(String uid, String role, String status);
}
