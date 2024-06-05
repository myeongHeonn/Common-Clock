package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.dto.AddPersonalTimetableRequest;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
}
