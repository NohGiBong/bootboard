package com.busanit.config;

import com.busanit.handler.CustomFormLoginSuccessHandler;
import com.busanit.handler.CustomSocialLoginSuccessHandler;
import com.busanit.service.MemberService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
                .authorizeHttpRequests(authorizeHttpRequestConfigurer -> authorizeHttpRequestConfigurer
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
                        .requestMatchers("/", "/member/**").permitAll()
                        .anyRequest().authenticated()
                )   // 인증별 권한 설정

                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/member/login")   // 주석 처리하면 기본 시큐리티 페이지로 사용 가능
                        //.loginProcessingUrl("/loginProcess")
                        .usernameParameter("email")
                        .failureUrl("/member/login/error")
                        //.defaultSuccessUrl("/")
                        .successHandler(authenticationFormLoginSuccessHandler())
                )   // loginForm and login Process setting

                .oauth2Login(OAuth2LoginConfigurer -> OAuth2LoginConfigurer
                        .loginPage("/member/login")
                        .failureUrl("/member/login/error")
                        //.defaultSuccessUrl("/")
                        .successHandler(authenticationSocialLoginSuccessHandler())
                )   // social login setting

                .logout(logoutConfigurer -> logoutConfigurer
                        //.logoutUrl("/logout")     // 주석 처리하면 기본 시큐리티 페이지로 사용 가능
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)    // 로그아웃 이후 세션 전체 삭제 여부
                        .clearAuthentication(true)      // 로그아웃 시 인증정보 삭제 여부
                )   // logout setting

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder의 해시 함수를 이용해서 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationFormLoginSuccessHandler() {
        return new CustomFormLoginSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSocialLoginSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

}










