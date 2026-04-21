package nbc.schedule.Auth.domain;

import jakarta.servlet.http.HttpSession;
import nbc.schedule.Auth.domain.Exception.AuthDomainException;
import nbc.schedule.common.exception.ErrorCode;

public final class SessionUtils {

    private SessionUtils() {}

    public static Long getCurrentUserId(HttpSession session) {
        if (session == null) {
            throw AuthDomainException.of(ErrorCode.AUTH_REQUIRED);
        }
        Object userId = session.getAttribute(AuthConst.LOGIN_USER);
        if (userId == null) {
            throw AuthDomainException.of(ErrorCode.AUTH_REQUIRED);
        }
        return (Long) userId;
    }
}