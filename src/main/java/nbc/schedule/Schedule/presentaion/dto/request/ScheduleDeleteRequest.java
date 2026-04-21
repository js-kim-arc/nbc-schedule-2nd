package nbc.schedule.Schedule.presentaion.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.Schedule.domain.Schedule;

@Getter
@NoArgsConstructor
public class ScheduleDeleteRequest {

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    private String password;

    public void validate(Schedule schedule) {
        schedule.validatePassword(this.password);
    }
}