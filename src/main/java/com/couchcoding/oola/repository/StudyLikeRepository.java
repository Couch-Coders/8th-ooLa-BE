package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyLikeRepository extends JpaRepository<StudyLike, Long> {
}
