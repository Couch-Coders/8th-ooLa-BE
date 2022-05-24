package com.couchcoding.oola.validation.exceptionhandler;

import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;
import com.couchcoding.oola.validation.error.ErrorResponse;
import com.couchcoding.oola.validation.ParameterBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    // 올바른 URL 요청이 아닌 경우 예외가 발생되었을때 Catch하여 ErrorResponse를 반환
    protected  ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("handleHttpRequestMethodNotSupportedException: {}", e);

        ErrorResponse response = ErrorResponse.create()
                                              .status(HttpStatus.METHOD_NOT_ALLOWED)
                                              .message(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    // @Valid 검증 실패시 Catch하여 ErrorResponse 반환
    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidParameterException(ParameterBadRequestException e) {
        logger.error("handleInvalidParameterException: {}", e);
        
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse.create()
                     .status(errorCode.getStatus())
                     .message(e.toString())
                     .errors(e.getErrors());
        
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // CustomException을 상속받은 클래스 예외가 발생되면 예외를 catch하여 ErrorResponse를 반환해 준다
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        logger.error("handleAllException: {}", e);

        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 모든 예외를 Catch하여 ErrorResponse 형식으로 반환
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("handleException: {}", e);

        ErrorResponse response = ErrorResponse.create()
                                              .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                              .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
