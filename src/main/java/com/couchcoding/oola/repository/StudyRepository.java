package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long>,StudyRepositoryCustom {

    @Override
    Optional<Study> findById(Long id);
}
