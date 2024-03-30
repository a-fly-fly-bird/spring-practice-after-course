package pers.terry.springpracticeaftercourse.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.filter.JwtAuthFilter;
import pers.terry.springpracticeaftercourse.handler.CustomAccessDeniedHandler;
import pers.terry.springpracticeaftercourse.handler.CustomUnauthorizedHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        private static final String[] WHITE_LIST_URL = {
                        "/user/**",
                        "/auth/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
        };

        private final JwtAuthFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        private final CustomAccessDeniedHandler customAccessDeniedHandler;
        private final CustomUnauthorizedHandler customUnauthorizedHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable).headers(headers -> headers.frameOptions().disable())
                                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                // 授权失败处理
                                .exceptionHandling(handling -> handling
                                                .accessDeniedHandler(
                                                                customAccessDeniedHandler))
                                // 认证失败处理
                                .exceptionHandling(handling -> handling.authenticationEntryPoint(
                                                customUnauthorizedHandler))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
