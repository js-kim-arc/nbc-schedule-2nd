package nbc.schedule.Schedule.application;

import lombok.RequiredArgsConstructor;
import nbc.schedule.Schedule.presentaion.dto.request.ScheduleCreateRequest;
import nbc.schedule.Schedule.presentaion.dto.request.ScheduleUpdateRequest;
import nbc.schedule.Schedule.presentaion.dto.response.ScheduleResponse;
import nbc.schedule.Schedule.domain.Exception.ScheduleDomainException;
import nbc.schedule.Schedule.domain.Schedule;
import nbc.schedule.Schedule.infrastructure.persistence.ScheduleRepository;
import nbc.schedule.common.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 기본: 읽기 전용. 쓰기는 메서드 레벨에서 @Transactional 재선언
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // -----------------------------------------------------------------------
    // 생성
    // -----------------------------------------------------------------------

    @Transactional
    public ScheduleResponse create(ScheduleCreateRequest request) {
        return ScheduleResponse.from(scheduleRepository.save(request.toSchedule()));
    }

    // -----------------------------------------------------------------------
    // 조회
    // -----------------------------------------------------------------------

    public Page<ScheduleResponse> findAll(String author, Pageable pageable) {
        Page<Schedule> page = (author != null && !author.isBlank())
                ? scheduleRepository.findByAuthor(author, pageable)
                : scheduleRepository.findAll(pageable);

        return page.map(ScheduleResponse::from);
    }

    public ScheduleResponse findById(Long id) {
        return ScheduleResponse.from(getScheduleOrThrow(id));
    }

    // -----------------------------------------------------------------------
    // 수정
    // -----------------------------------------------------------------------

    @Transactional
    public ScheduleResponse update(Long id, ScheduleUpdateRequest request) {
        Schedule schedule = getScheduleOrThrow(id);
        request.applyTo(schedule); // dirty checking → @Transactional 종료 시 UPDATE 자동 실행
        return ScheduleResponse.from(schedule);
    }

    // -----------------------------------------------------------------------
    // 삭제
    // -----------------------------------------------------------------------

    @Transactional
    public void delete(Long id, String password) {
        Schedule schedule = getScheduleOrThrow(id);
        schedule.validatePassword(password);
        scheduleRepository.delete(schedule);
    }

    // -----------------------------------------------------------------------
    // 내부 헬퍼
    // -----------------------------------------------------------------------

    private Schedule getScheduleOrThrow(Long id) {
        return scheduleRepository.findById(id)
                                 .orElseThrow(() -> ScheduleDomainException.of(
                                         ErrorCode.SCHEDULE_NOT_FOUND, "id=" + id));
    }
}