package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    @Override
    Optional<Study> findById(Long aLong);
}
