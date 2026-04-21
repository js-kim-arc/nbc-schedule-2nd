package nbc.schedule.Auth.domain.Exception;

import nbc.schedule.common.exception.BusinessException;
import nbc.schedule.common.exception.ErrorCode;

public class AuthDomainException extends BusinessException {

    private AuthDomainException(ErrorCode errorCode) {
        super(errorCode);
    }

    private AuthDomainException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    public static AuthDomainException of(ErrorCode errorCode) {
        return new AuthDomainException(errorCode);
    }

    public static AuthDomainException of(ErrorCode errorCode, String detail) {
        return new AuthDomainException(errorCode, detail);
    }
}
