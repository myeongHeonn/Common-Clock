package fourmation.CommonClock.repository;


import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.domain.TeamTimetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamTimetableRepository extends JpaRepository<TeamTimetable, Long> {
    TeamTimetable findTeamTimetableByTeam(Team team);
}
