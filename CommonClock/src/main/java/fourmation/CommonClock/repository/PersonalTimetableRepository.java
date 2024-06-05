package fourmation.CommonClock.repository;

import fourmation.CommonClock.domain.PersonalTimetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTimetableRepository extends JpaRepository<PersonalTimetable, Long> {
}
