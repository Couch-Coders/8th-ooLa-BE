package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyCommentRepository extends JpaRepository<Comment, Long> {
}
