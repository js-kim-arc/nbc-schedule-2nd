package nbc.schedule.Schedule.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.Schedule.domain.Exception.ScheduleDomainException;
import nbc.schedule.User.domain.User;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "schedule")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --------------------------------------------------------------------
    // 생성
    // --------------------------------------------------------------------

    private Schedule(User user, String title, String content, String password) {
        validateTitle(title);
        validateContent(content);
        validatePasswordNotBlank(password);

        this.user     = user;
        this.title    = title;
        this.content  = content;
        this.password = password;
    }

    public static Schedule of(User user, String title, String content, String password) {
        return new Schedule(user, title, content, password);
    }

    // --------------------------------------------------------------------
    // 행위
    // --------------------------------------------------------------------

    public void update(String title, String content, String password) {
        validatePassword(password);

        if (title != null) {
            validateTitle(title);
            this.title = title;
        }
        if (content != null) {
            validateContent(content);
            this.content = content;
        }
    }

    public void validatePassword(String password) {
        if (!Objects.equals(this.password, password)) {
            throw ScheduleDomainException.of(ErrorCode.SCHEDULE_PASSWORD_MISMATCH);
        }
    }

    // --------------------------------------------------------------------
    // 불변식 검증 (private)
    // --------------------------------------------------------------------

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw ScheduleDomainException.of(ErrorCode.SCHEDULE_TITLE_BLANK);
        }
        if (title.length() > 100) {
            throw ScheduleDomainException.of(ErrorCode.INVALID_INPUT, "제목은 100자를 초과할 수 없습니다.");
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw ScheduleDomainException.of(ErrorCode.SCHEDULE_CONTENT_BLANK);
        }
        if (content.length() > 1000) {
            throw ScheduleDomainException.of(ErrorCode.INVALID_INPUT, "내용은 1000자를 초과할 수 없습니다.");
        }
    }

    private static void validatePasswordNotBlank(String password) {
        if (password == null || password.isBlank()) {
            throw ScheduleDomainException.of(ErrorCode.SCHEDULE_PASSWORD_BLANK);
        }
    }
}