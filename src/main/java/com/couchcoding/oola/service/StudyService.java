package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;

import com.couchcoding.oola.dto.study.response.StudyRoleResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;

import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyMemberRepositoryCustom;
import com.couchcoding.oola.repository.StudyRepository;
import com.couchcoding.oola.repository.StudyRepositoryCustom;
import com.couchcoding.oola.validation.MemberForbiddenException;

import com.couchcoding.oola.validation.StudyNotFoundException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyRepositoryCustom studyRepositoryCustom;
    private final StudyMemberRepositoryCustom studyMemberRepositoryCustom;
    private final MemberService memberService;
    private final FirebaseAuth firebaseAuth;

    // 스터디 만들기
    @Transactional
    public StudyResponseDto createStudy(Study study) {
        Study saved = studyRepository.saveAndFlush(study);
        return new StudyResponseDto(saved);
    }

    // 스터디 단건 조회 (비로그인)
    public StudyRoleResponseDto studyDetail(Long studyId) {
        Study study = getStudy(studyId);
        // 비로그인 이므로 role은 general
        StudyRoleResponseDto studyRoleResponseDto = new StudyRoleResponseDto(study, "general");
        log.info("비로그인 스터디 조회: {}", studyRoleResponseDto.toString());
        return studyRoleResponseDto;
    }

    // 로그인
    public StudyRoleResponseDto studyDetail(Long studyId, String header) throws FirebaseAuthException {
        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(header);
        Member member = (Member) memberService.loadUserByUsername(firebaseToken.getUid());

        Study study = getStudy(studyId);

        List<StudyMember> studyMembers = study.getStudyMembers();
        StudyRoleResponseDto studyRoleResponseDto = null;
        for (StudyMember studyMember : studyMembers) {
            if (member.getUid().equals(studyMember.getMember().getUid()) && studyId == studyMember.getStudyId()) {
                String role = studyMember.getRole();
                Long stId = studyMember.getStudyId();
                studyRoleResponseDto = new StudyRoleResponseDto(study, role);
            } else {
                studyRoleResponseDto = new StudyRoleResponseDto(study, "general");
            }
        }
        // 리더로 참여하면 leader , 회원으로 참여하면 member, 참여하지 않으면 general
        log.info("로그인 하여 조회한 스터에서 사용자의 역할: {}", studyRoleResponseDto.toString());
        return studyRoleResponseDto;
    }

    // 스터디 조건 검색 및 페이징 처리
    public Page<Study> findByAllCategory(Pageable pageable, String studyType, String studyDays,
                                         String timeZone , String status, String studyName) {
        return studyRepositoryCustom.findAllBySearchOption(pageable ,studyType, studyDays, timeZone,status , studyName);
    }

    // 스터디 수정
    @Transactional
    public Study studyUpdate(Long studyId, StudyRequestDto requestDto , Member member) {
        Study study = null;
        // 스터디 생성자와 로그인 유저가 같은지 비교
        Study result = getStudy(studyId);
        if (result.getCreateUid().equals(member.getUid())) {
            Study updated = result.update(studyId, requestDto, member.getUid());
            study = studyRepository.save(updated);
        } else {
            throw new MemberForbiddenException();
        }
        return study;
    }

    // 스터디 완료시 스터디 상태 수정
    @Transactional
    public Study studyComplete(Long studyId, Member member, StudyRequestDto studyRequestDto) {
        Study study = null;
        // 스터디 생성자와 로그인 유저가 같은지 비교
        Study result = getStudy(studyId);
        if (result.getCreateUid().equals(member.getUid())) {
            result = result.updateCompleteStatus(studyRequestDto.getStatus());
            study = studyRepository.save(result);
        } else {
            throw new MemberForbiddenException();
        }
        return study;
    }

    // 클라이언트에게 반환해주기 위해 엔티티를 dto 객체로 변환
    public StudyResponseDetailDto toDto(Study study) {
        StudyResponseDetailDto studyResponseDetailDto = new StudyResponseDetailDto();
        studyResponseDetailDto = studyResponseDetailDto.toDto(study);
        return studyResponseDetailDto;
    }

    // 스터디에 대한 정보 조회회
    public Study getStudy(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> {
            throw new StudyNotFoundException();
        });
    }
}