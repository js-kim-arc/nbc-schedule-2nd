package nbc.schedule.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 부가 상세 메시지가 필요한 경우 (ex. "제목은 100자를 초과할 수 없습니다.")
    public BusinessException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage() + " — " + detail);
        this.errorCode = errorCode;
    }

    public static BusinessException withDetail(ErrorCode errorCode, String detail) {
        return new BusinessException(errorCode, detail);
    }
}
