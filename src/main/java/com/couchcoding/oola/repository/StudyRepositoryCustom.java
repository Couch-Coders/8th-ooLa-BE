package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyRepositoryCustom {
    Page<Study> findAllBySearchOption(Pageable pageable, String studyType , String studyDays, String timeZone , String status, String studyName);
}
