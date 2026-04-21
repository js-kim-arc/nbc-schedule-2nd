package nbc.schedule.common.config;

import lombok.RequiredArgsConstructor;
import nbc.schedule.Auth.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.ObjectMapper;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(objectMapper))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/login",   // 로그인 — 인증 불필요
                        "/api/users"     // POST 회원가입 — 인증 불필요
                        // GET /api/users/** 는 AuthInterceptor 내부 GET 분기로 처리
                                    );
    }
}
