package fourmation.CommonClock.repository;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.TeamTimetable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTimetableRepository extends JpaRepository<PersonalTimetable, Long> {
    List<PersonalTimetable> findPersonalTimetablesByTeamTimetable(TeamTimetable teamTimetable);
    PersonalTimetable findPersonalTimetableByName(String name);
}
