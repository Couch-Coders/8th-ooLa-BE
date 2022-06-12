package com.couchcoding.oola.config;

import com.couchcoding.oola.security.filter.JwtFilter;
import com.couchcoding.oola.security.filter.MockJwtFilter;
import com.couchcoding.oola.service.MemberService;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// spring 실행환경이 테스트용인지 배포용인지에 따라 AuthFilterContainer를 초기화 해준다
@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    private final MemberService userService;
    private final FirebaseAuth firebaseAuth;

    @Bean
    @Profile("local") // spring 실행시 환경을 @Profile 어노테이션을 통해 구분한다
    public AuthFilterContainer mockAuthFilter() {
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new MockJwtFilter(userService));
        return authFilterContainer;
    }

    @Bean
    @Profile({"prod","default"})
    public AuthFilterContainer firebaseAuthFilter() {
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new JwtFilter(userService, firebaseAuth));
        return authFilterContainer;
    }
}