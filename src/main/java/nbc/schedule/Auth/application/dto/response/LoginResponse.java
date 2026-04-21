package nbc.schedule.Auth.application.dto.response;

import lombok.Getter;
import nbc.schedule.User.domain.User;

@Getter
public class LoginResponse {

    private final Long   id;
    private final String username;
    private final String email;

    private LoginResponse(User user) {
        this.id       = user.getId();
        this.username = user.getUsername();
        this.email    = user.getEmail();
    }

    public static LoginResponse from(User user) {
        return new LoginResponse(user);
    }
}
