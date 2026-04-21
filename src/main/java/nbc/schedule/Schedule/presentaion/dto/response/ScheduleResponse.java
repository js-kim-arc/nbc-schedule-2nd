package nbc.schedule.Schedule.presentaion.dto.response;

import lombok.Getter;
import nbc.schedule.Schedule.domain.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private final Long          id;
    private final Long          userId;
    private final String        username; // JOIN 없이 응답 편의상 포함
    private final String        title;
    private final String        content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ScheduleResponse(Schedule schedule) {
        this.id        = schedule.getId();
        this.userId    = schedule.getUser().getId();
        this.username  = schedule.getUser().getUsername();
        this.title     = schedule.getTitle();
        this.content   = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(schedule);
    }
}