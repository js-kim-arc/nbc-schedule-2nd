package nbc.schedule.Auth.application;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import nbc.schedule.Auth.application.dto.request.LoginRequest;
import nbc.schedule.Auth.application.dto.response.LoginResponse;
import nbc.schedule.Auth.domain.AuthConst;
import nbc.schedule.Auth.domain.Exception.AuthDomainException;
import nbc.schedule.User.domain.User;
import nbc.schedule.User.infrastructure.persistence.UserRepository;
import nbc.schedule.common.config.BcryptPasswordEncoder;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final BcryptPasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request, HttpSession session) {
        // 1. 이메일로 유저 조회 — 없으면 즉시 동일한 401
        // 과제 피드백 반영 - 이메일을 찾을 때 user리포지토리가 dto 내부에서 의존하게 됨 - userService 참고
        User user = userRepository.findByEmail(request.getEmail())
                                  .orElseThrow(() -> AuthDomainException.of(
                                          ErrorCode.AUTH_INVALID_CREDENTIALS));

        // 2. 비밀번호 검증 — BCrypt matches(). 불일치 시 동일한 401
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw AuthDomainException.of(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        // 3. 세션에 userId 저장 (User 객체 전체가 아닌 Long 하나만)
        session.setAttribute(AuthConst.LOGIN_USER, user.getId());

        return LoginResponse.from(user);
    }
}
