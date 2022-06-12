package com.couchcoding.oola.security.filter;

import com.couchcoding.oola.util.RequestUtil;
import com.couchcoding.oola.validation.error.CustomException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 정보를 검증하는 JwtFilter
// 헤더에 담긴 authorization 토큰을 검증하고 유저 정보를 확인한다
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // get the token from the request
        FirebaseToken decodedToken;
        try{
            String header = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
            decodedToken = firebaseAuth.verifyIdToken(header);//디코딩한 firebase 토큰을 반환
            log.error("토큰: {}" , decodedToken.toString());
        } catch (FirebaseAuthException | IllegalArgumentException | CustomException e) {
            log.error("403 에러: {}" , e.getMessage());
            // ErrorMessage 응답 전송
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
            return;
        }

        // User를 가져와 SecurityContext에 저장한다.
        try{
            UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());//uid 를 통해 회원 엔티티 조회
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());//인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);//securityContextHolder 에 인증 객체 저장
            log.error(authentication.getPrincipal().toString());
            log.error(user.getAuthorities().toString());
        } catch(UsernameNotFoundException e){
            log.error("404 에러: {}" , e.getMessage());
            // ErrorMessage 응답 전송
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}