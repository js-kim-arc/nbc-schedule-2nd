package nbc.schedule.User.presentaion.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.User.domain.User;
import nbc.schedule.common.exception.BusinessException;
import nbc.schedule.common.exception.ErrorCode;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @Size(max = 50, message = "유저명은 50자를 초과할 수 없습니다.")
    private String username;

    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    public void validate() {
        if (this.username == null && this.email == null) {
            throw BusinessException.withDetail(ErrorCode.INVALID_INPUT, "수정할 필드가 없습니다.");
        }
    }

    public boolean hasEmailChange() {
        return this.email != null;
    }

    public void applyTo(User user) {
        user.update(this.username, this.email);
    }

    /** 이메일 중복 확인용 — hasEmailChange() 가 true 일 때만 Service 가 호출한다 */
    public String getEmail() {
        return this.email;
    }
}
