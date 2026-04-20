package nbc.schedule.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex,
                                                        HttpServletRequest req) {
        ErrorCode ec = ex.getErrorCode();
        return ResponseEntity
                .status(ec.getStatus())
                .body(new ErrorResponse(
                        ec.getCode(),
                        ex.getMessage(),    // detail이 포함된 메시지 사용
                        req.getRequestURI(),
                        OffsetDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ErrorCode.INVALID_INPUT.getCode(),
                        ErrorCode.INVALID_INPUT.getMessage(),
                        req.getRequestURI(),
                        OffsetDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex,
                                                          HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "SERVER_ERROR",
                        "예기치 않은 오류가 발생했습니다.",
                        req.getRequestURI(),
                        OffsetDateTime.now()
                ));
    }


    @Getter
    @AllArgsConstructor
    static class ErrorResponse {
        private String        code;
        private String        message;
        private String        path;
        private OffsetDateTime timestamp;
    }
}