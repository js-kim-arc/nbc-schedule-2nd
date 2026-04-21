package nbc.schedule.Schedule.presentaion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.Schedule.domain.Schedule;
import nbc.schedule.common.exception.BusinessException;
import nbc.schedule.common.exception.ErrorCode;

@Getter
@NoArgsConstructor
public class ScheduleUpdateRequest {

    // null: 변경 안 함 / 값이 있으면: 길이만 선 검증, blank는 도메인이 처리
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    private String title;

    @Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
    private String content;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    public void validate() {
        if (this.title == null && this.content == null) {
            throw BusinessException.withDetail(ErrorCode.INVALID_INPUT, "수정할 필드가 없습니다.");
        }
    }

    public void applyTo(Schedule schedule) {
        schedule.update(this.title, this.content, this.password);
    }
}