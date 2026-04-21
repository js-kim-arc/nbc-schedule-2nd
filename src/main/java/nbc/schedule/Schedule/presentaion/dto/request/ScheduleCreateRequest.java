package nbc.schedule.Schedule.presentaion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.Schedule.domain.Schedule;
import nbc.schedule.User.domain.User;

@Getter
@NoArgsConstructor
public class ScheduleCreateRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    @Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
    private String content;

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    private String password;

    public Long getUserId() {
        return this.userId;
    }

    public Schedule toSchedule(User user) {
        return Schedule.of(user, this.title, this.content, this.password);
    }
}