package com.couchcoding.oola.dto.studymember.response;

import com.couchcoding.oola.entity.Member;
import com.couchcoding.oola.entity.Study;
import com.couchcoding.oola.entity.StudyMember;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudyMemberResponseDto {

    private Member member;
    private Study study;
}
