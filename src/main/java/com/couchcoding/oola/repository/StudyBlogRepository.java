package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyBlogRepository extends JpaRepository<StudyBlog, Long>, StudyBlogRespositoryCustom {
}
