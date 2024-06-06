package fourmation.CommonClock.repository;

import fourmation.CommonClock.domain.CommonTimetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonTimetableRepository extends JpaRepository<CommonTimetable, Long> {
}
