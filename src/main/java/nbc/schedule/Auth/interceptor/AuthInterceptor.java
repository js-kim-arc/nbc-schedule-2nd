package nbc.schedule.Auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import nbc.schedule.Auth.domain.AuthConst;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        // GET 요청은 모두 공개로 허용 (목록 조회, 단건 조회)
        if (HttpMethod.GET.matches(request.getMethod())) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(AuthConst.LOGIN_USER) == null) {
            sendUnauthorized(response, request.getRequestURI());
            return false;
        }

        return true;
    }

    // -----------------------------------------------------------------------
    // 내부 헬퍼
    // -----------------------------------------------------------------------

    private void sendUnauthorized(HttpServletResponse response, String path) throws IOException {
        ErrorCode ec = ErrorCode.AUTH_REQUIRED;

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code",      ec.getCode());
        body.put("message",   ec.getMessage());
        body.put("path",      path);
        body.put("timestamp", OffsetDateTime.now().toString());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}