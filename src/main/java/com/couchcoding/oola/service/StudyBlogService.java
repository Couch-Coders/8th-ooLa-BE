package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studyblogs.request.StudyBlogRequestDto;
import com.couchcoding.oola.dto.studyblogs.response.StudyBlogListResponseDto;
import com.couchcoding.oola.dto.studyblogs.response.StudyBlogMemberResponseDto;
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
    private final StudyBlogRepository studyBlogRepository;

    public StudyBlogResponseDto  blogs(StudyBlogRequestDto studyBlogRequestDto, Member member , Long studyId) {

        Study study = studyService.getStudy(studyId);
        Member result = getMember(study, member);

        StudyBlog studyBlog = new StudyBlog(studyBlogRequestDto, result, study);
        StudyBlog entity = studyBlogRepository.save(studyBlog);
        StudyBlogResponseDto studyBlogResponseDto = new StudyBlogResponseDto(entity);
        return studyBlogResponseDto;
    }

    public StudyBlogListResponseDto getBlogs(Long studyId) {
       Study study =  studyService.getStudy(studyId);
       List<StudyMember> studyMembers = study.getStudyMembers();
       List<StudyBlogMemberResponseDto> studyBlogMemberResponseDtos = new ArrayList<>();
       for (StudyMember studyMember : studyMembers) {
           String uid = studyMember.getUid();
           String blogUrl = studyMember.getMember().getBlogUrl();
           String githubUrl = studyMember.getMember().getGithubUrl();
           List<String> techStack = studyMember.getMember().getTechStack();
           String role = studyMember.getRole();
           StudyBlogMemberResponseDto studyBlogMemberResponseDto = new StudyBlogMemberResponseDto(uid, blogUrl, githubUrl, techStack, role);
           studyBlogMemberResponseDtos.add(studyBlogMemberResponseDto);
       }

       StudyBlogListResponseDto studyBlogListResponseDto = new StudyBlogListResponseDto(studyBlogMemberResponseDtos, study.getStudyBlogs());
       return studyBlogListResponseDto;
    }

    // Member에 대한 권한 검사
    public Member getMember(Study study ,  Member member) {
        Member result = null;
        List<StudyMember> studyMembers = study.getStudyMembers();
        for (StudyMember studyMember : studyMembers) {
            if (studyMember.getMember().getUid().equals(member.getUid())) {
                result = studyMember.getMember();
            }
        }

        if (result == null) {
            throw new MemberForbiddenException();
        }
        return result;
    }
}
