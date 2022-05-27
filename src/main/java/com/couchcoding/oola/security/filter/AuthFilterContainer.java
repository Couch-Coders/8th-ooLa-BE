package com.couchcoding.oola.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

// 필터를 담고 있는 컨테이너
// 인증 및 인가후 redirect 되는 경우 이미 실행되었던 필터가 다시 실행되지 않도록 하기 위해 OncePerRequestFilter를 사용한다
public class AuthFilterContainer {
    private OncePerRequestFilter authFilter;

    public void setAuthFilter(OncePerRequestFilter authFilter) {
        this.authFilter = authFilter;
    }

    public OncePerRequestFilter getAuthFilter() {
        return authFilter;
    }
}
