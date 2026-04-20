package nbc.schedule.Schedule.domain.Exception;

import nbc.schedule.common.exception.BusinessException;
import nbc.schedule.common.exception.ErrorCode;

public class ScheduleDomainException extends BusinessException {

    private ScheduleDomainException(ErrorCode errorCode) {
        super(errorCode);
    }

    private ScheduleDomainException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    /** ErrorCode만으로 예외 생성. */
    public static ScheduleDomainException of(ErrorCode errorCode) {
        return new ScheduleDomainException(errorCode);
    }

    public static ScheduleDomainException of(ErrorCode errorCode, String detail) {
        return new ScheduleDomainException(errorCode, detail);
    }
}
