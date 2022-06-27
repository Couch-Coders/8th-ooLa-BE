package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Comment;

import java.util.List;

public interface StudyCommentRepositoryCustom {

    List<Comment> findAllByStudyId(Long studyId);
}
