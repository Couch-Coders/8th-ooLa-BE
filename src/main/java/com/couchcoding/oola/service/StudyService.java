package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.study.request.StudyRequestDto;
import com.couchcoding.oola.dto.study.response.StudyListResponseDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDetailDto;
import com.couchcoding.oola.dto.study.response.StudyResponseDto;

import com.couchcoding.oola.dto.study.response.StudyRoleResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeListResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeStatus;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;

import com.couchcoding.oola.entity.StudyLike;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyLikeRepository;
import com.couchcoding.oola.repository.StudyMemberRepository;
import com.couchcoding.oola.repository.StudyMemberRepositoryCustom;
import com.couchcoding.oola.repository.StudyRepository;
import com.couchcoding.oola.validation.MemberForbiddenException;

import com.couchcoding.oola.validation.StudyNotFoundException;

import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberService studyMemberService;
    private final StudyMemberRepository studyMemberRepository;
    private final MemberService memberService;
    private final FirebaseAuth firebaseAuth;

    // 스터디 만들기
    @Transactional
    public StudyResponseDto createStudy(StudyRequestDto studyRequestDto, Member member) {
        Study study = studyRequestDto.toEntity(studyRequestDto, member);

        // 스터디에 참여하는 멤버 생성
        StudyMember studyMember = StudyMember.builder()
                .member(member)
                .study(study)
                .role("leader")
                .build();
        Study saved = studyRepository.saveAndFlush(study);
        studyMemberService.setStudyLeader(studyMember);
        return new StudyResponseDto(saved);
    }

    // 스터디 단건 조회 (비로그인)
    public StudyRoleResponseDto studyDetail(Long studyId) {
        Study study = getStudy(studyId);
        // 비로그인 이므로 role은 general
        StudyRoleResponseDto studyRoleResponseDto = new StudyRoleResponseDto(study, "general" , false);
        return studyRoleResponseDto;
    }

    // 로그인후 스터디 상세 조회
    public StudyRoleResponseDto studyDetail(Long studyId, String header) {
        Member member = null;
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(header);
            member = (Member) memberService.loadUserByUsername(firebaseToken.getUid());
        } catch (UsernameNotFoundException | FirebaseAuthException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.MemberNotFound);
        }

        Study study = getStudy(studyId);
        // 조회하는 스터디에 대한 관심등록 여부
        Boolean likeStatus = getStudyLikeStatus(study, member);

        // 조회하는 스터디에 대한 role 판단
        List<StudyMember> studyMembers = studyMemberRepository.findAllByStudyId(studyId);
        StudyRoleResponseDto studyRoleResponseDto = getRole(studyMembers, study, member, likeStatus);
        return studyRoleResponseDto;
    }

    // 스터디 조건 검색 및 페이징 처리 (로그인)
    public Page<Study> findByAllCategory(Pageable pageable, String studyType, String studyDays,
                                         String timeZone , String status, String studyName) {
        Page<Study> studies = studyRepository.findAllBySearchOption(pageable ,studyType, studyDays, timeZone,status , studyName);
        return studies;
    }

    // 스터디 수정
    @Transactional
    public Study studyUpdate(Long studyId, StudyRequestDto requestDto , Member member) {
        Study result = getStudy(studyId);
        List<StudyMember> studyMembers = result.getStudyMembers();

        Study updated = null;
        for (StudyMember studyMember : studyMembers) {
            if (studyMember.isLeader(member.getUid())) {
                updated = result.update(studyId, requestDto, member.getUid());
            }
        }

        if (updated == null) {
            throw new MemberForbiddenException();
        }
        return studyRepository.save(updated);
    }

    // 스터디 완료시 스터디 상태 수정
    @Transactional
    public Study studyComplete(Long studyId, Member member, StudyRequestDto studyRequestDto) {
        Study study = null;
        // 스터디 생성자와 로그인 유저가 같은지 비교
        Study result = getStudy(studyId);
        List<StudyMember> studyMembers = result.getStudyMembers();
        String uid = null;
        for (StudyMember studyMember : studyMembers) {
            if (studyMember.getRole().equals("leader"))  {
                uid =  studyMember.getMember().getUid();
            }
        }
        if (uid.equals(member.getUid())) {
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

    // 스터디에 대한 정보 조회
    public Study getStudy(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> {
            throw new StudyNotFoundException();
        });
    }

    // 조회하는 스터디에 대한 관심등록 여부 -> 관심등록 되있는 경우 : True / 안되있는 경우 : False
    public Boolean getStudyLikeStatus(Study study , Member member) {
        StudyLike studyLike = null;
        List<StudyLike> studyLikes = study.getStudyLikes();
        if (studyLikes.size() > 0) {
            for (int i = 0; i < studyLikes.size(); i++) {
                if (studyLikes.get(i).getMember().getUid().equals(member.getUid())) {
                    studyLike  = studyLikes.get(i);
                }
            }
        }

        if (studyLike == null) {
            return false;
        }
        return studyLike.getLikeStatus();
    }

    // 조회하는 스터디에 대한 role 판단
    public StudyRoleResponseDto getRole(List<StudyMember> studyMembers, Study study, Member member, Boolean likeStatus) {
        String role = null;
        StudyRoleResponseDto studyRoleResponseDto = null;
        for (int i = 0; i < studyMembers.size(); i++) {
            if (studyMembers.get(i).getMember().getUid().equals(member.getUid())) {
                role = studyMembers.get(i).getRole();
            }
        }

        if (role == null) {
            if (likeStatus == null) {
                studyRoleResponseDto = new StudyRoleResponseDto(study, "general" , false);

            } else  {
                studyRoleResponseDto = new StudyRoleResponseDto(study, "general" , likeStatus);
            }
        } else {
            if (likeStatus == null) {
                studyRoleResponseDto = new StudyRoleResponseDto(study, role , false);

            } else {
                studyRoleResponseDto = new StudyRoleResponseDto(study, role, likeStatus);
            }
        }
        return studyRoleResponseDto;
    }


}
