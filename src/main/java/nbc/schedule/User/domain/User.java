package nbc.schedule.User.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.User.domain.Exception.UserDomainException;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시용. 외부 기본 생성 차단
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // -----------------------------------------------------------------------
    // 생성
    // -----------------------------------------------------------------------

    private User(String username, String email, String password) {
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);

        this.username = username;
        this.email    = email;
        this.password = password;
    }

    public static User of(String username, String email, String password) {
        return new User(username, email, password);
    }

    // -----------------------------------------------------------------------
    // 행위
    // -----------------------------------------------------------------------
    public void update(String username, String email) {
        if (username != null) {
            validateUsername(username);
            this.username = username;
        }
        if (email != null) {
            validateEmail(email);
            this.email = email;
        }
        // updatedAt은 @LastModifiedDate가 dirty checking 시점에 자동 갱신
    }

    /**
     * 비밀번호 일치 여부 검증.
     */
    public void validatePassword(String rawPassword) {
        if (!Objects.equals(this.password, rawPassword)) {
            throw UserDomainException.of(ErrorCode.USER_PASSWORD_MISMATCH);
        }
    }

    // -----------------------------------------------------------------------
    // 불변식 검증 (private)
    // 도메인 규칙 위반은 모두 UserDomainException + ErrorCode 체계로 던진다.
    // -----------------------------------------------------------------------

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw UserDomainException.of(ErrorCode.USER_USERNAME_BLANK);
        }
        if (username.length() > 50) {
            throw UserDomainException.of(ErrorCode.INVALID_INPUT, "유저명은 50자를 초과할 수 없습니다.");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw UserDomainException.of(ErrorCode.USER_EMAIL_BLANK);
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw UserDomainException.of(ErrorCode.USER_PASSWORD_BLANK);
        }
        if (password.length() < 8) {
            throw UserDomainException.of(ErrorCode.USER_PASSWORD_TOO_SHORT);
        }
    }
}
