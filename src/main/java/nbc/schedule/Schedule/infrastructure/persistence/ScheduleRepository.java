package nbc.schedule.Schedule.infrastructure.persistence;

import nbc.schedule.Schedule.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findByUserId(Long userId, Pageable pageable);
}

