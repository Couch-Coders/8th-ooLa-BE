package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studyblogs.request.StudyBlogRequestDto;
import com.couchcoding.oola.dto.studyblogs.response.StudyBlogListResponseDto;
import com.couchcoding.oola.dto.studyblogs.response.StudyBlogResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyBlog;
import com.couchcoding.oola.entity.StudyMember;
import com.couchcoding.oola.repository.StudyBlogRepository;
import com.couchcoding.oola.repository.StudyBlogRespositoryCustom;
import com.couchcoding.oola.validation.MemberForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyBlogService {

    private final StudyService studyService;
    private final StudyBlogRepository studyBlogRepository;

    public StudyBlogResponseDto  blogs(StudyBlogRequestDto studyBlogRequestDto, Member member , Long studyId) {

        Study study = studyService.getStudy(studyId);
        getMember(study, member);

        StudyBlog studyBlog = new StudyBlog(studyBlogRequestDto, member, study);
        StudyBlog entity = studyBlogRepository.save(studyBlog);
        StudyBlogResponseDto studyBlogResponseDto = new StudyBlogResponseDto(entity);
        return studyBlogResponseDto;
    }

    public Study getBlogs(Long studyId) {
       Study study =  studyService.getStudy(studyId);
       return study;
    }

    // Member에 대한 권한 검사
    public void getMember(Study study ,  Member member) {
        List<StudyMember> studyMembers = study.getStudyMembers();
        int j  = 0;
        for (StudyMember studyMember : studyMembers) {
            if (!studyMember.getMember().getUid().equals(member.getUid())) {
                j += 1;
                if (j == studyMembers.size()) {
                    throw new MemberForbiddenException();
                }

            }
        }
    }
}
