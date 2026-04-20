package nbc.schedule.User.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.User.domain.User;

@Getter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "유저명은 비어 있을 수 없습니다.")
    @Size(max = 50, message = "유저명은 50자를 초과할 수 없습니다.")
    private String username;

    @NotBlank(message = "이메일은 비어 있을 수 없습니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    public User toUser() {
        return User.of(this.username, this.email, this.password);
    }

    /** 이메일 중복 확인용 — Service가 email 값만 필요할 때 사용 */
    public String getEmail() {
        return this.email;
    }
}
