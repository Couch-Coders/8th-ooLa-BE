package com.couchcoding.oola.service;

import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyMemberSerivce {

    private final StudyMemberRepository studyMemberRepository;

    public StudyMember create(StudyMember studyMember) {
        StudyMember result = studyMemberRepository.saveAndFlush(studyMember);
        return result;
    }
}
