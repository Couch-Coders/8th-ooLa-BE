package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyMember;

import java.util.List;

public interface StudyMemberRepositoryCustom {

    List<StudyMember> findByStudyId(Long studyId);
    List<StudyMember> findAllByUidAndRole(Long uid, String role);
}
