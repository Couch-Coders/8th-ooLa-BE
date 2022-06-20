package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.StudyLike;

import java.util.List;

public interface StudyLikeRepositoryCustom {
    List<StudyLike> findAllByLikeStatusAndUid(Boolean likeStatus, String uid);
}
