package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studyblogs.request.StudyBlogRequestDto;
import com.couchcoding.oola.dto.studyblogs.response.LIstResponseDto;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyBlogService {

    private final StudyService studyService;
    private final StudyMemberService studyMemberService;
    private final StudyBlogRepository studyBlogRepository;
    private final StudyBlogRespositoryCustom studyBlogRespositoryCustom;



    public StudyBlogResponseDto  blogs(StudyBlogRequestDto studyBlogRequestDto, Member member , Long studyId) {

        StudyMember result = null;
        Study study = studyService.getStudy(studyId);
        List<StudyMember> studyMembers = study.getStudyMembers();
        for (StudyMember studyMember : studyMembers) {
            if (studyMember.getMember().getUid().equals(member.getUid())) {
                result = studyMember;
            }
        }

        if (result == null) {
            throw new MemberForbiddenException();
        }
        
        StudyBlog studyBlog = new StudyBlog(studyBlogRequestDto, member, study);
        StudyBlog entity = studyBlogRepository.save(studyBlog);
        StudyBlogResponseDto studyBlogResponseDto = new StudyBlogResponseDto(entity);
        return studyBlogResponseDto;
    }


    public List<StudyBlog> getBlogs(Long studyId) {
        List<StudyBlog> studyBlogs = studyBlogRespositoryCustom.findAllByStudyId(studyId);
        return studyBlogs;
    }
}
