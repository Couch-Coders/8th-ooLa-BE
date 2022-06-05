package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Study;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyRepositoryCustom {

    Page<Study> findBySearchOption(Pageable pageable, String studyType , String studyDays, String timeZone , String status);

    Page<Study> findBySearchOption(Pageable pageable, String studyType , String studyDays, String timeZone);

    Page<Study> findBySearchOption(Pageable pageable, String studyType , String studyDays);

    Page<Study> findBySearchOption(Pageable pageable, String studyType );
}
