package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;

import com.couchcoding.oola.repository.StudyRepository;
import com.couchcoding.oola.repository.impl.StudyRepositoryImpl;
import com.couchcoding.oola.validation.MemberForbiddenException;

import com.couchcoding.oola.validation.MemberNotFoundException;
import com.couchcoding.oola.validation.StudyNotFoundException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyRepositoryImpl studyRepositoryImpl;
    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    // 스터디 만들기
    @Transactional
    public StudyResponseDto createStudy(Study study) {
        Study saved = studyRepository.saveAndFlush(study);
        return new StudyResponseDto(saved);
    }

    // 스터디 단건 조회
    public Study studyDetail(Long studyId) {
       return (Study) studyRepository.findById(studyId).orElseThrow(() -> {
            throw new StudyNotFoundException();
        });
    }

    // 스터디 조건 검색 및 페이징 처리
    public Page<Study> findByAllCategory(Pageable pageable, String studyType, String studyDays, String timeZone , String status, String studyName) {
        return studyRepositoryImpl.findBySearchOption(pageable ,studyType, studyDays, timeZone,status , studyName);
    }

    // 스터디 수정
    @Transactional
    public Study studyUpdate(Long studyId, StudyRequestDto requestDto , String uid) {
        Study updated = null;
        // 스터디 생성자와 로그인 유저가 같은지 비교
        Study result = studyDetail(studyId);
        if (result.getCreateUid().equals(uid)) {
            Study before = studyDetail(studyId);
            updated = before.update(studyId, requestDto, uid);
            studyRepository.save(updated);
        } else {
            throw new MemberForbiddenException();
        }

        return updated;
    }

    // 스터디 완료시 스터디 상태 수정
    @Transactional
    public Study studyComplete(Long studyId, String uid , StudyRequestDto requestDto) {
        Study updated = null;
        // 스터디 생성자와 로그인 유저가 같은지 비교
        Study result = studyDetail(studyId);
        if (result.getCreateUid().equals(uid)) {
            updated = result.updateCompleteStatus(studyId, "완료" , requestDto);
            studyRepository.save(updated);
        } else {
            throw new MemberForbiddenException();
        }

        return updated;
    }

    // 클라이언트에게 반환해주기 위해 엔티티를 dto 객체로 변환
    public StudyResponseDetailDto toDto(Study study) {
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto = studyResponseDetailDto.toDto(study);
        return studyResponseDetailDto;
    }
}