package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyMember;

import java.util.List;

public interface StudyMemberRepositoryCustom {

    List<StudyMember> findByStudyId(Long studyId);
    List<StudyMember> findAllByUidAndRole(Long uid, String role);
    List<StudyMember> findAllByUidAndRoleAndStatus(Long uid, String role, String status);
    List<StudyMember> findAllByUidOrStudyId(Long uid , Long studyId);
}
