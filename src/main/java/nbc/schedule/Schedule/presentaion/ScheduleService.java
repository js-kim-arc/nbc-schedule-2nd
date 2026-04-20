package nbc.schedule.Schedule.presentaion;

import lombok.RequiredArgsConstructor;
import nbc.schedule.Schedule.application.dto.request.ScheduleCreateRequest;
import nbc.schedule.Schedule.application.dto.request.ScheduleUpdateRequest;
import nbc.schedule.Schedule.application.dto.response.ScheduleResponse;
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
        Schedule schedule = Schedule.of(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
                                       );
        return ScheduleResponse.from(scheduleRepository.save(schedule));
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

    /**
     * 단건 일정 조회.
     */
    public ScheduleResponse findById(Long id) {
        return ScheduleResponse.from(getScheduleOrThrow(id));
    }

    // -----------------------------------------------------------------------
    // 수정
    // -----------------------------------------------------------------------

    /**
     * 일정 수정 (PATCH 시맨틱).
     */
    @Transactional
    public ScheduleResponse update(Long id, ScheduleUpdateRequest request) {
        if (request.getTitle() == null && request.getContent() == null) {
            throw ScheduleDomainException.of(ErrorCode.INVALID_INPUT, "수정할 필드가 없습니다.");
        }

        Schedule schedule = getScheduleOrThrow(id);
        schedule.update(request.getTitle(), request.getContent(), request.getPassword());

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
