package com.couchcoding.oola.service;

import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    public StudyMember create(StudyMember studyMember) {
        StudyMember result = studyMemberRepository.saveAndFlush(studyMember);
        return result;
    }

}
