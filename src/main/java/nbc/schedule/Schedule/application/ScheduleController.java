package nbc.schedule.Schedule.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.schedule.Schedule.application.dto.request.ScheduleCreateRequest;
import nbc.schedule.Schedule.application.dto.request.ScheduleDeleteRequest;
import nbc.schedule.Schedule.application.dto.request.ScheduleUpdateRequest;
import nbc.schedule.Schedule.application.dto.response.ScheduleResponse;
import nbc.schedule.Schedule.presentaion.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // -----------------------------------------------------------------------
    // POST /api/schedules — 일정 생성
    // -----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @Valid @RequestBody ScheduleCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.create(request));
    }

    // -----------------------------------------------------------------------
    // GET /api/schedules — 일정 목록 페이징 조회
    // -----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<Page<ScheduleResponse>> findAll(
            @RequestParam(required = false) String author,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(scheduleService.findAll(author, pageable));
    }

    // -----------------------------------------------------------------------
    // GET /api/schedules/{id} — 단건 일정 조회
    // -----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(scheduleService.findById(id));
    }

    // -----------------------------------------------------------------------
    // PATCH /api/schedules/{id} — 일정 수정
    // -----------------------------------------------------------------------

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequest request) {

        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    // -----------------------------------------------------------------------
    // DELETE /api/schedules/{id} — 일정 삭제
    // -----------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDeleteRequest request) {

        scheduleService.delete(id, request.getPassword());
        return ResponseEntity.noContent().build();
    }
}
