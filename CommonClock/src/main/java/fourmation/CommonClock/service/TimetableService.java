package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.domain.TeamTimetable;
import fourmation.CommonClock.dto.response.GetMainResponseDTO;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import fourmation.CommonClock.repository.TeamTimetableRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final TeamRepository teamRepository;
    private final TeamTimetableRepository teamTimetableRepository;
    private final PersonalTimetableRepository personalTimetableRepository;

    public Long createPersonalTimeTable(Long teamPk){
        TeamTimetable teamTimetable = getTeamTimetable(teamPk);
        PersonalTimetable personalTimetable = PersonalTimetable
            .builder()
            .name("user")
            .teamTimetable(teamTimetable)
            .build();
        return personalTimetableRepository.save(personalTimetable).getId();
    }

    public GetMainResponseDTO getMain(Long teamPk){
        TeamTimetable teamTimetable = getTeamTimetable(teamPk);
        List<PersonalTimetable> personalTimetableList = personalTimetableRepository.findPersonalTimetablesByTeamTimetable(teamTimetable);
        List<String> userNames = personalTimetableList.stream()
            .map(PersonalTimetable::getName)
            .toList();

        return new GetMainResponseDTO(personalTimetableList.size(), userNames);
    }

    private TeamTimetable getTeamTimetable(Long teamPk){
        Team team = teamRepository.findById(teamPk).orElseThrow(() -> new NoSuchElementException("없음"));
        return teamTimetableRepository.findTeamTimetableByTeam(team);
    }

}
