package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studymember.response.StudyMemberResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyMemberRepository;
import com.couchcoding.oola.repository.StudyRepository;
import com.couchcoding.oola.repository.impl.StudyMemberRepositoryImpl;
import com.couchcoding.oola.validation.StudyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StudyMemberService {
    private final StudyMemberRepositoryImpl studyMemberRepositoryImpl;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    // 스터디 참여자 정보조회
    @Transactional
    public List<StudyMember> studyMembers(Long studyId) {
        return studyMemberRepositoryImpl.findByStudyId(studyId);
    }

    // 스터디 개설시 리더정보 추가
    public StudyMember studyLeaders(StudyMember studyMember) {
        return studyMemberRepository.saveAndFlush(studyMember);
    }

    // 스터디 참여 신청 (멤버)
    public StudyMemberResponseDto studyMemberEnroll(Member member, Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(() -> {
            throw new StudyNotFoundException();
        });

        StudyMember studyMember = StudyMember.builder()
                .study(study)
                .role("member")
                .member(member)
                .build();

        StudyMember entityResult = studyMemberRepository.saveAndFlush(studyMember);

        int updateParticipants = study.getCurrentParticipants() + 1;
        Study entity = study.updateCurrentParticipants( updateParticipants);
        Study updated = studyRepository.saveAndFlush(entity);
        StudyMemberResponseDto studyMemberResponseDto = StudyMemberResponseDto.builder()
                .study(updated)
                .member(entityResult.getMember())
                .build();
        return studyMemberResponseDto;
    }
}