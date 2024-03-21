package com.busanit.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .cors(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorizeHttpRequestConfigurer -> authorizeHttpRequestConfigurer
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
                        .requestMatchers("/", "/member/**").permitAll()
                        .anyRequest().authenticated()
                )   // 인증별 권한 설정

                .formLogin(formLoginConfigurer -> formLoginConfigurer
//                        .loginPage("/member/login")
                        .loginProcessingUrl("/loginProcess")
                        .defaultSuccessUrl("/")
                    // loginForm and login Process setting
                )

                .logout(LogoutConfigurer -> LogoutConfigurer
//                      .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)        // 로그아웃 이후 세션 전체 삭제 여부
                        .clearAuthentication(true)          // 로그아웃 시 인증정보 삭제 여부
                    // logout setting
                )

                .build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // BCryptPasswordEncoder의 해시 함수를 이용해서 비밀번호 암호화
//        return new BCryptPasswordEncoder();
//    }
}
