package com.couchcoding.oola.validation.error;

import com.couchcoding.oola.validation.StudyLikeException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 공통 예외
   // 잘못된 요청인 경우 :404 :Not Found
   URLNotFound(HttpStatus.NOT_FOUND, "C001", "잘못된 요청입니다."),
   // 검증에 실패한 경우 : 400 : Bad Request
   ParameterBadRequest(HttpStatus.BAD_REQUEST, null, "요청파라미터가 잘못되었습니다."),

    // 회원 예외
    // 로그인이 필요한 경우 :403 (인증되지 않은 회원일때) : Forbidden
    LoginForbidden(HttpStatus.FORBIDDEN, "C002", "로그인이 필요합니다."),

    // 회원을 찾을 수 없는 경우 :404 Not Found
    MemberNotFound(HttpStatus.NOT_FOUND, "C003", "존재하지 않는 회원입니다."),

    // 이미 가입된 회원인 경우 :404 Not Found
    MemberExist(HttpStatus.NOT_FOUND, "C004", "이미 가입된 회원입니다."),

    // 로그인 성공, but 우리 사이트에 회원가입이 되지 않은 경우
    MemberNotRegistered(HttpStatus.CREATED, "C004_1", "로그인은 성공하였으나 저희 사이트에 회원가입이 필요합니다."),

    // 인증 인가 예외
    // 요청에 권한이 없는 경우 :인증은 되었지만 호출하는 API에 권한이 없는 경우 :403(Forbidden)
    MemberUnAuthorized(HttpStatus.FORBIDDEN, "C005", "인증되지 않은 회원입니다."),

    // 인증 정보가 부정확한 경우 :401 :인증되지 않은 회원인 경우 :UnAutorized
    MemberForbidden(HttpStatus.UNAUTHORIZED, "C006", "요청을 처리할 권한이 없습니다."),

    // 스터디 예외
    // 이미 관심등록한 스터디인 경우 :404 : Not Found
    StudyExist(HttpStatus.NOT_FOUND, "C007", "이미 관심등록된 스터디 입니다."),

    // 스터디를 찾을 수 없는 경우 : 404 : Not Found
    StudyNotFound(HttpStatus.NOT_FOUND, "C008", "존재하지 않는 스터디 입니다."),

    // 스터디 필터링 결과 스터디를 찾을 수 없는 경우 : 404 : Not Found
    StudySearchNotFound(HttpStatus.NOT_FOUND, "C009", "스터디에 대한 검색결과를 찾을 수 없습니다."),

    // 댓글 예외
    // 삭제된 댓글을 조회하는 경우 :404 : Not Found
    CommentNotFound(HttpStatus.NOT_FOUND, "C0010", "삭제되어 존재하지 않는 댓글입니다."),

    InvalidAuthorization(HttpStatus.NOT_FOUND, "C0011", "인증 정보가 부정확합니다"),

   // 괌심 등록되 있지 않은 스터디를 관심 해제 하려고 할때
    StudyNotLikeException(HttpStatus.NOT_FOUND, "C0012","관심등록 되 있지 않은 스터디 입니다"),

   // 이미 관심등록된 스터디를 또 관심등록하는 경우
    StudyLikeException(HttpStatus.FORBIDDEN, "C0013", "이미 관심등록된 스터디 입니다");



    private final HttpStatus status;

    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
