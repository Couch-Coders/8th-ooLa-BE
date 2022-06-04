package com.couchcoding.oola.repository;

import com.couchcoding.oola.entity.Study;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyRepositoryCustom {

    List<Study> findBySearchOption( String studyType , String studyDays, String timeZone , String status);

    List<Study> findBySearchOption( String studyType , String studyDays, String timeZone);

    List<Study> findBySearchOption(String studyType , String studyDays);

    List<Study> findBySearchOption( String studyType );

}
