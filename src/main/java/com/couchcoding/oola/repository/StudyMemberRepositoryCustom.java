package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyMember;

import java.util.List;

public interface StudyMemberRepositoryCustom {

    List<StudyMember> findAllByStudyId(Long studyId);
    List<StudyMember> findAllByUidAndRole(String uid, String role);
    List<StudyMember> findAllByUidAndRoleAndStatus(String uid, String role, String status);
}
