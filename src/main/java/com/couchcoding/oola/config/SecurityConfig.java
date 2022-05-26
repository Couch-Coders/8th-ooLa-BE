package com.couchcoding.oola.config;

import com.couchcoding.oola.security.filter.AuthFilterContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// spring security 관련 설정
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthFilterContainer authFilterContainer;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable() // Http basic Auth 기반으로 로그인 인증창이 뜨는데 disable 시 인증창이 뜨지 않는다
                .csrf().disable() // rest api이므로 csrf 보안이 필요없다
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 토큰으로 인증하므로 stateless 해야 한다 (인증정보를 서버에 담아두지 않겠다)
                .and()
                .authorizeRequests() // 요청에 대한 권한 지정
                .anyRequest().authenticated() // 모든 요청이 인증되어야 한다
                .and()
                .addFilterBefore(authFilterContainer.getAuthFilter(), UsernamePasswordAuthenticationFilter.class); //컨트롤러로 요청이 넘어가기 전에 검증이 필요한 요청인 경우 해당 유저의 로긍니 정보를 검증하는 JwtFilter가 실행된다
    }

    // spring security 인증시 제외될 항목 정의
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/member")
                .antMatchers(HttpMethod.GET, "/exception/**")
                .antMatchers("/")
                .antMatchers("/css/**")
                .antMatchers("/static/**")
                .antMatchers("/js/**")
                .antMatchers("/img/**")
                .antMatchers("/fonts/**")
                .antMatchers("/vendor/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/pages/**")
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**");
    }
}