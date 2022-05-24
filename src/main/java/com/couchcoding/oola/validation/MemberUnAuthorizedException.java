package com.couchcoding.oola.validation;

import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;

public class MemberUnAuthorizedException extends CustomException {

    private static final long serialVersionUID = -2116671122895194101L;

    public MemberUnAuthorizedException() {
        super(ErrorCode.MemberUnAuthorized);
    }
}
