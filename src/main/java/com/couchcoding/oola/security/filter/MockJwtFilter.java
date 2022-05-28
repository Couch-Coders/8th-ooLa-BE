package com.couchcoding.oola.security.filter;

import com.couchcoding.oola.service.MemberService;
import com.couchcoding.oola.util.RequestUtil;
import com.couchcoding.oola.validation.error.CustomException;
import com.couchcoding.oola.validation.error.ErrorCode;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
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

// 테스트를 위한 MockJwtFilter
@RequiredArgsConstructor
public class MockJwtFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 디코딩 및 확인된 Firebase 토큰
        // uid 와 사용자 속성을 가져오는데 사용할 수 있다
        FirebaseToken decodedToken;
        String header;
        try {
            header = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
        } catch (CustomException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);// 유효한 토큰이 아닌경우, 인증 정보가 부족하여 인증이 거부되었다
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
            return;
        }

        try {
            UserDetails user = memberService.loadUserByUsername(header);// uid를 사용하여 유저 정보를 불러온다

            // 인증이 끝나고 SecurityContextHolder.getContext에 등록될 Authentication 객체
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());// 세션 쿠키 방식의 인증이 이루어 진다
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);// SecurityContextHolder에 인증객체(authenticationToken)를 저장한다
        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
            return;
        }
        filterChain.doFilter(request, response);// 다음 차례 필터 클래스 객체의 doFilter 메서드를 호출하는 기능을 한다
        // 더이상 실행될 필터가 없다면 request 객체와 response 객체를 서블릿으로 넘겨 처리한다
    }
}
