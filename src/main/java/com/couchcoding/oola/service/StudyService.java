package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;

import com.couchcoding.oola.entity.Study;

import com.couchcoding.oola.repository.StudyRepository;
import com.couchcoding.oola.validation.MemberForbiddenException;

import com.couchcoding.oola.validation.StudyNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

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