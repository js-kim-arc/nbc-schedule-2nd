package nbc.schedule.User.domain.Exception;

import nbc.schedule.common.exception.BusinessException;
import nbc.schedule.common.exception.ErrorCode;

public class UserDomainException extends BusinessException {

    private UserDomainException(ErrorCode errorCode) {
        super(errorCode);
    }

    private UserDomainException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    public static UserDomainException of(ErrorCode errorCode) {
        return new UserDomainException(errorCode);
    }

    public static UserDomainException of(ErrorCode errorCode, String detail) {
        return new UserDomainException(errorCode, detail);
    }
}