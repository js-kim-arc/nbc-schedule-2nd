package nbc.schedule.Schedule.application.dto.response;

import lombok.Getter;
import nbc.schedule.Schedule.domain.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private final Long          id;
    private final String        title;
    private final String        content;
    private final String        author;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ScheduleResponse(Schedule schedule) {
        this.id        = schedule.getId();
        this.title     = schedule.getTitle();
        this.content   = schedule.getContent();
        this.author    = schedule.getAuthor();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(schedule);
    }
}
