package nbc.schedule.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ─── 공통 ─────────────────────────────────────────────────────────
    INVALID_INPUT("C001", "잘못된 입력 값입니다.",     HttpStatus.BAD_REQUEST),
    NOT_FOUND    ("C002", "데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // ─── Schedule ─────────────────────────────────────────────────────
    SCHEDULE_NOT_FOUND       ("SCHEDULE001", "해당 일정을 찾을 수 없습니다.",        HttpStatus.NOT_FOUND),
    SCHEDULE_PASSWORD_MISMATCH("SCHEDULE002", "비밀번호가 일치하지 않습니다.",       HttpStatus.FORBIDDEN),
    SCHEDULE_TITLE_BLANK     ("SCHEDULE003", "제목은 비어 있을 수 없습니다.",        HttpStatus.BAD_REQUEST),
    SCHEDULE_CONTENT_BLANK   ("SCHEDULE004", "내용은 비어 있을 수 없습니다.",        HttpStatus.BAD_REQUEST),
    SCHEDULE_AUTHOR_BLANK    ("SCHEDULE005", "작성자는 비어 있을 수 없습니다.",      HttpStatus.BAD_REQUEST),
    SCHEDULE_PASSWORD_BLANK  ("SCHEDULE006", "비밀번호는 비어 있을 수 없습니다.",    HttpStatus.BAD_REQUEST),

    // ─── User ─────────────────────────────────────────────────────────
    USER_NOT_FOUND          ("USER001", "해당 유저를 찾을 수 없습니다.",           HttpStatus.NOT_FOUND),
    USER_EMAIL_DUPLICATE    ("USER002", "이미 사용 중인 이메일입니다.",             HttpStatus.CONFLICT),
    USER_USERNAME_BLANK     ("USER003", "유저명은 비어 있을 수 없습니다.",          HttpStatus.BAD_REQUEST),
    USER_EMAIL_BLANK        ("USER004", "이메일은 비어 있을 수 없습니다.",          HttpStatus.BAD_REQUEST),
    USER_PASSWORD_BLANK     ("USER005", "비밀번호는 비어 있을 수 없습니다.",        HttpStatus.BAD_REQUEST),
    USER_PASSWORD_TOO_SHORT ("USER006", "비밀번호는 8자 이상이어야 합니다.",        HttpStatus.BAD_REQUEST),
    USER_PASSWORD_MISMATCH  ("USER007", "비밀번호가 일치하지 않습니다.",            HttpStatus.FORBIDDEN),

    // ─── Comment ──────────────────────────────────────────────────────
    COMMENT_NOT_FOUND        ("COMMENT001", "해당 댓글을 찾을 수 없습니다.",                        HttpStatus.NOT_FOUND),
    COMMENT_PASSWORD_MISMATCH("COMMENT002", "비밀번호가 일치하지 않습니다.",                        HttpStatus.FORBIDDEN),
    COMMENT_LIMIT_EXCEEDED   ("COMMENT003", "댓글은 일정당 최대 10개까지 작성할 수 있습니다.",       HttpStatus.BAD_REQUEST),
    COMMENT_CONTENT_BLANK    ("COMMENT004", "댓글 내용은 비어 있을 수 없습니다.",                   HttpStatus.BAD_REQUEST),
    COMMENT_AUTHOR_BLANK     ("COMMENT005", "작성자는 비어 있을 수 없습니다.",                      HttpStatus.BAD_REQUEST),
    COMMENT_PASSWORD_BLANK   ("COMMENT006", "비밀번호는 비어 있을 수 없습니다.",                    HttpStatus.BAD_REQUEST);

    // ─── 필드 ─────────────────────────────────────────────────────────
    private final String     code;
    private final String     message;
    private final HttpStatus status;
}
