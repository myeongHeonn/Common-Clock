package fourmation.CommonClock.service;

import fourmation.CommonClock.common.ApiResponse;
import fourmation.CommonClock.common.ApiResponseGenerator;
import fourmation.CommonClock.domain.Event;
import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.domain.TeamTimetable;
import fourmation.CommonClock.dto.request.AppendEventRequestDTO;
import fourmation.CommonClock.dto.response.AppendEventResponseDTO;
import fourmation.CommonClock.dto.response.EventListResponseDTO;
import fourmation.CommonClock.repository.EventRepository;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import fourmation.CommonClock.repository.TeamTimetableRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final TeamRepository teamRepository;
    private final TeamTimetableRepository teamTimetableRepository;
    private final PersonalTimetableRepository personalTimetableRepository;
    private final EventRepository eventRepository;

    public AppendEventResponseDTO appendEvent(AppendEventRequestDTO appendEventRequestDTO){
        Long teamPk = appendEventRequestDTO.getTeamPk();
        String personalName = appendEventRequestDTO.getPersonalName();

        List<PersonalTimetable> personalTimetableList = getAllPersonalTimetableByTeamTimetable(teamPk);

        // personalName과 일치하는 PersonalTimetable 객체를 찾음
        PersonalTimetable personalTimetable = personalTimetableList.stream()
            .filter(pt -> pt.getName().equals(personalName))
            .findFirst()
            .orElse(null);

        if (personalTimetable == null) {
            throw new NoSuchElementException("dd"); // 빈 리스트 반환
        }

        Event event = Event.builder()
            .personalTimetable(personalTimetable)
            .allDay(appendEventRequestDTO.isAllDay())
            .title(appendEventRequestDTO.getTitle())
            .start(appendEventRequestDTO.getStart())
            .end(appendEventRequestDTO.getEnd())
            .backgroundColor(appendEventRequestDTO.getBackgroundColor())
            .textColor(appendEventRequestDTO.getTextColor())
            .build();
        Long eventPk = eventRepository.save(event).getId();
        return new AppendEventResponseDTO(eventPk);
    }

    private List<PersonalTimetable> getAllPersonalTimetableByTeamTimetable(Long teamPk){
        TeamTimetable teamTimetable = getTeamTimetable(teamPk);
        return personalTimetableRepository.findPersonalTimetablesByTeamTimetable(teamTimetable);
    }
    private TeamTimetable getTeamTimetable(Long teamPk){
        Team team = teamRepository.findById(teamPk).orElseThrow(() -> new NoSuchElementException("없음"));
        return teamTimetableRepository.findTeamTimetableByTeam(team);
    }
}
