package com.couchcoding.oola.security.filter;

import com.couchcoding.oola.validation.error.CustomException;
import com.google.firebase.auth.FirebaseAuth;
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

// 로그인 정보를 검증하는 JwtFilter
// 헤더에 담긴 authorization 토큰을 검증하고 유저 정보를 확인한다
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // DB에서 유저 정보를 가져오는 역할을 한다
    // DB의 유저 정보를 가져와서 AuthenticationProvider 인터페이스로 유저 정보를 반환하면
    // 사용자가 입력한 정보와 DB에 있는 유저 정보를 비교한다
    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 디코딩 및 확인된 Firebase 토큰
        // uid 와 사용자 속성을 가져오는데 사용할 수 있다
        FirebaseToken decodedToken;

        try {
            String header = request.getHeader("Authorization");
            decodedToken =firebaseAuth.verifyIdToken(header);// 토큰이 유효한지 확인한다 (토큰이 올바르게 서명 되었는지 확인한다)
        } catch (FirebaseAuthException | IllegalArgumentException | CustomException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);// 유효한 토큰이 아닌경우, 인증 정보가 부족하여 인증이 거부되었다
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
            return;
        }

        try {
            UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());// uid를 사용하여 유저 정보를 불러온다
            
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
