package nbc.schedule.Schedule.presentaion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedule.Schedule.domain.Schedule;

@Getter
@NoArgsConstructor
public class ScheduleCreateRequest {

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    @Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
    private String content;

    @NotBlank(message = "작성자는 비어 있을 수 없습니다.")
    @Size(max = 50, message = "작성자는 50자를 초과할 수 없습니다.")
    private String author;

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    private String password;

    public Schedule toSchedule() {
        return Schedule.of(this.title, this.content, this.author, this.password);
    }
}