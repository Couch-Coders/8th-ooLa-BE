package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyLike;
import com.couchcoding.oola.repository.impl.StudyLikeRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyLikeRepository extends JpaRepository<StudyLike, Long> , StudyLikeRepositoryCustom {


}
