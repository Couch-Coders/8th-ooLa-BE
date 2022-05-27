package com.couchcoding.oola.security.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

// 토큰을 검증하는 Filter
// spring security 요청에 따라 검증진행
@AllArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        FirebaseToken decodedToken;
        String header = request.getHeader("Authorization"); // Authorization Header를 가져온다
        if (header == null || !header.startsWith("Bearer ")) {
            setUnauthorizedResponse(response, "INVALID_HEADER");
            return;
        }

        String token = header.substring(7);// Authorization Header에서 Token을 가져온다

        try {
            decodedToken = firebaseAuth.verifyIdToken(token);// firebaseAuth를 사용하여 Token을 검증한다
        } catch (FirebaseAuthException e) {
            setUnauthorizedResponse(response, "INVALID_TOKEN");
            return;
        }

        try {
            UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());//Firebase에서 제공하는 uid를 사용하여 사용자 정보를 가져온다
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());// username 과 password를 조합하여 UsernamePasswordAuthenticationToken 을 생성한다
            SecurityContextHolder.getContext().setAuthentication(authentication);// 인증에 성공하면 SecurityContext에 사용자 정보를 추가한다
        } catch (NoSuchElementException e) {
                setUnauthorizedResponse(response, "USER_NOT_FOUND");// 인증에 실패한 경우
                return;
        }
        filterChain.doFilter(request, response);// filer가 요청을 가로재서 spring security에게 권한 부여를 체크하도록 넘겨준다
    }

    // firebase 토큰이 유효하지 않은 경우
    private void setUnauthorizedResponse(HttpServletResponse response, String code) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }
}
