package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studylikes.request.StudyHateRequestDto;
import com.couchcoding.oola.dto.studylikes.request.StudyLikeRequestDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeResponseDto;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyLike;
import com.couchcoding.oola.repository.StudyLikeRepository;
import com.couchcoding.oola.validation.ParameterBadRequestException;
import com.couchcoding.oola.validation.StudyNotFoundException;
import com.couchcoding.oola.validation.StudyNotLikeException;
import com.couchcoding.oola.validation.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudyLikeService {

    private final StudyService studyService;
    private final StudyLikeRepository studyLikeRepository;

    // 스터디 관심 등록
    public StudyLikeResponseDto myStudyLikes(Member member, Long studyId , StudyLikeRequestDto studyLikeRequestDto) {
        Study study = studyService.getStudy(studyId);

        StudyLike studyLike = new StudyLike(member, study, studyLikeRequestDto.isLikeStatus());
        StudyLike entity = studyLikeRepository.save(studyLike);
        StudyLikeResponseDto studyLikeResponseDto = new StudyLikeResponseDto(entity.getId(),member.getUid(), study.getStudyId(), entity.isLikeStatus());
        return studyLikeResponseDto;
    }

    // 스터디 관심 해제
    public void myStudyHates(Member member, Long studyId, StudyHateRequestDto studyHateRequestDto) {
        studyService.getStudy(studyId);
       StudyLike studyLike = studyLikeRepository.findById(studyHateRequestDto.getId()).orElseThrow(() -> {
            throw new StudyNotLikeException();
       });
        studyLikeRepository.delete(studyLike);
    }




}
