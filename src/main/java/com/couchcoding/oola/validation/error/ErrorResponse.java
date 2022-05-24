package com.couchcoding.oola.validation.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 예외에 대한 응답 정보를 저장하는 클래스
@Getter
@NoArgsConstructor
public class ErrorResponse {

    // 예외가 발생한 시간
    private LocalDateTime timestamp = LocalDateTime.now();

    // 예외 메시지 저장
    private String message;

    // 예외를 세분화하기 위한 사용자 지정 코드
    private String code;

    // HTTP 상태 값
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;


    static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    public ErrorResponse status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse errors(Errors errors) {
        setCustomFieldErrors(errors.getFieldErrors());
        return this;
    }

    public void setCustomFieldErrors(List<FieldError> fieldErrors) {

        customFieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> {
            customFieldErrors.add(new CustomFieldError(
                    error.getCodes()[0],
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        });
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }
}
