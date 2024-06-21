package fourmation.CommonClock.repository;

import fourmation.CommonClock.domain.Event;
import fourmation.CommonClock.domain.PersonalTimetable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByPersonalTimetable(PersonalTimetable personalTimetable);
}
