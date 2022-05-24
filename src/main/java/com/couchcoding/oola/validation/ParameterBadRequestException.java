package com.couchcoding.oola.validation;

import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ParameterBadRequestException extends CustomException {

    private static final long serialVersionUID = -2116671122895194101L;

    private final Errors errors;

    public ParameterBadRequestException(Errors errors) {
        super(ErrorCode.ParameterBadRequest);
        this.errors = errors;
    }
}
