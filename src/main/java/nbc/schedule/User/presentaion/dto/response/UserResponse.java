package nbc.schedule.User.presentaion.dto.response;

import lombok.Getter;
import nbc.schedule.User.domain.User;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final Long          id;
    private final String        username;
    private final String        email;
    // password 필드 없음 — 선언 자체가 없으므로 JSON 직렬화 불가능
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private UserResponse(User user) {
        this.id        = user.getId();
        this.username  = user.getUsername();
        this.email     = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    public static UserResponse from(User user) {
        return new UserResponse(user);
    }
}
