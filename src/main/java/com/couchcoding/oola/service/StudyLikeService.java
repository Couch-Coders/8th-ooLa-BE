package com.couchcoding.oola.service;

import com.couchcoding.oola.dto.studylikes.request.StudyHateRequestDto;
import com.couchcoding.oola.dto.studylikes.request.StudyLikeRequestDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeListResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeResponseDto;
import com.couchcoding.oola.dto.studylikes.response.StudyLikeStatus;
import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyLike;
import com.couchcoding.oola.repository.StudyLikeRepository;
import com.couchcoding.oola.validation.ParameterBadRequestException;
import com.couchcoding.oola.validation.StudyLikeException;
import com.couchcoding.oola.validation.StudyNotFoundException;
import com.couchcoding.oola.validation.StudyNotLikeException;
import com.couchcoding.oola.validation.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyLikeService {

    private final StudyService studyService;
    private final StudyLikeRepository studyLikeRepository;

    // 스터디 관심 등록
    public StudyLikeResponseDto LikeMyStudy(Member member, Long studyId , StudyLikeRequestDto studyLikeRequestDto) {
        Study study = studyService.getStudy(studyId);
        StudyLike studyLike = new StudyLike(member, study, studyLikeRequestDto.isLikeStatus());

        for (int i = 0; i < study.getStudyLikes().size(); i++) {
            if (study.getStudyId() == study.getStudyLikes().get(i).getStudy().getStudyId() && member.getUid().equals(study.getStudyLikes().get(i).getMember().getUid())) {
                throw new StudyLikeException();
            }
        }

        StudyLike entity = studyLikeRepository.save(studyLike);
        StudyLikeResponseDto studyLikeResponseDto = new StudyLikeResponseDto(entity.getId(),member.getUid(), study.getStudyId(), entity.getLikeStatus());
        return studyLikeResponseDto;
    }

    // 스터디 관심 해제
    public void deleteMyStudy(Member member, Long studyId, StudyHateRequestDto studyHateRequestDto) {
        studyService.getStudy(studyId);
        StudyLike studyLike = studyLikeRepository.findById(studyHateRequestDto.getId()).orElseThrow(() -> {
            throw new StudyNotLikeException();
        });
        studyLikeRepository.delete(studyLike);
    }

    // 관심스터디 목록 조회
    public List<StudyLikeStatus> getMyStudysLikes(Member member) {
        // likeStatus가 true이고 // memberUid가 uid를 사용하여 조회
        Boolean likeStatus = true;
        List<StudyLike> studyLikes = studyLikeRepository.findAllByLikeStatusAndUid(likeStatus, member.getUid());
        List<StudyLikeStatus> studyLikeStatus = new ArrayList<>();

        for (StudyLike studyLike : studyLikes) {
            Study study = studyLike.getStudy();
            Boolean status = studyLike.getLikeStatus();
            StudyLikeStatus result = new StudyLikeStatus(study, status);
            studyLikeStatus.add(result);
        }
        return studyLikeStatus;
    }


}
