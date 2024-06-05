package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.dto.request.AddPersonalTimetableRequest;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PersonalTimetableService {

    private final PersonalTimetableRepository personalTimetableRepository;
    private final TeamRepository teamRepository;

    public PersonalTimetable save(AddPersonalTimetableRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid team ID: " + request.getTeamId()));;
        return personalTimetableRepository.save(request.toEntity(team));
    }

    // 해당 팀에 해당하는 시간표 찾기
    public List<PersonalTimetable> findByTeamId(Long teamId) {
        List<PersonalTimetable> timetables = personalTimetableRepository.findByTeamId(teamId);
        if (timetables.isEmpty()) {
            throw new IllegalArgumentException("Not found: " + teamId);
        }
        return timetables;
    }
}
