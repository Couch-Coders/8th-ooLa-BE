package com.couchcoding.oola.validation;

import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;

public class InvalidAuthorization extends CustomException {
    public InvalidAuthorization() {
        super(ErrorCode.InvalidAuthorization);
    }
}
