package fourmation.CommonClock.repository;

import fourmation.CommonClock.domain.PersonalTimetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalTimetableRepository extends JpaRepository<PersonalTimetable, Long> {
    List<PersonalTimetable> findByTeamId(Long teamId);
}
