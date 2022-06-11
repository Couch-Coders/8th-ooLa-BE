package com.couchcoding.oola.service;

import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyMemberRepository;
import com.couchcoding.oola.repository.StudyRepository;
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
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public List<StudyMember> studyMembers(Long studyId) {
        Study study = (Study) studyRepository.findById(studyId).orElseThrow( () ->  {
                    throw new StudyNotFoundException();
                }
        );

        log.info("study: {}" , study.toString());
        return studyMemberRepository.findByStudy(study);
    }
}
