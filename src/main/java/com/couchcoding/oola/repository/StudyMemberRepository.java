package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findAllByStudyMember(Study study);

}
