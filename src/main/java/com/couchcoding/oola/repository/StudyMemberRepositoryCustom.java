package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyMember;

import java.util.List;

public interface StudyMemberRepositoryCustom {

    List<StudyMember> findAllByStudyId(Long studyId);
    List<StudyMember> findAllByUidAndRole(Long uid, String role);
    List<StudyMember> findAllByUidAndRoleAndStatus(Long uid, String role, String status);
}
