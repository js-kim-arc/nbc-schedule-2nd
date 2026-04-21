package nbc.schedule.Schedule.presentaion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.schedule.Schedule.application.ScheduleService;
import nbc.schedule.Schedule.presentaion.dto.request.ScheduleCreateRequest;
import nbc.schedule.Schedule.presentaion.dto.request.ScheduleDeleteRequest;
import nbc.schedule.Schedule.presentaion.dto.request.ScheduleUpdateRequest;
import nbc.schedule.Schedule.presentaion.dto.response.ScheduleResponse;
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

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @Valid @RequestBody ScheduleCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleResponse>> findAll(
            @RequestParam(required = false) Long userId, // author → userId 전환
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(scheduleService.findAll(userId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequest request) {

        request.validate();
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDeleteRequest request) {

        scheduleService.delete(id, request.getPassword());
        return ResponseEntity.noContent().build();
    }
}
