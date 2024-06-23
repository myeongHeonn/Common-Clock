package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.Event;
import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.domain.TeamTimetable;
import fourmation.CommonClock.dto.response.EventListResponseDTO;
import fourmation.CommonClock.dto.response.EventResponseDTO;
import fourmation.CommonClock.dto.response.GetMainResponseDTO;
import fourmation.CommonClock.repository.EventRepository;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import fourmation.CommonClock.repository.TeamTimetableRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final TeamRepository teamRepository;
    private final TeamTimetableRepository teamTimetableRepository;
    private final PersonalTimetableRepository personalTimetableRepository;
    private final EventRepository eventRepository;

    public Long createPersonalTimeTable(Long teamPk){
        TeamTimetable teamTimetable = getTeamTimetable(teamPk);

        int countPersonalTable = getAllPersonalTimetableByTeamTimetable(teamPk).size();
        PersonalTimetable personalTimetable = PersonalTimetable
            .builder()
            .name("user" + countPersonalTable)
            .teamTimetable(teamTimetable)
            .build();
        return personalTimetableRepository.save(personalTimetable).getId();
    }

    public GetMainResponseDTO getMain(Long teamPk){
        List<PersonalTimetable> personalTimetableList = getAllPersonalTimetableByTeamTimetable(teamPk);
        List<String> userNames = personalTimetableList.stream()
            .map(PersonalTimetable::getName)
            .toList();

        return new GetMainResponseDTO(personalTimetableList.size(), userNames);
    }
    public EventListResponseDTO getPersonalCalender(Long teamPk, String personalName) {
        List<PersonalTimetable> personalTimetableList = getAllPersonalTimetableByTeamTimetable(teamPk);

        // personalName과 일치하는 PersonalTimetable 객체를 찾음
        PersonalTimetable personalTimetable = personalTimetableList.stream()
            .filter(pt -> pt.getName().equals(personalName))
            .findFirst()
            .orElse(null);

        if (personalTimetable == null) {
            return new EventListResponseDTO(List.of()); // 빈 리스트 반환
        }

        // PersonalTimetable에 해당하는 Event 리스트를 조회
        List<Event> eventList = eventRepository.findEventsByPersonalTimetable(personalTimetable);

        // Event 리스트를 EventResponseDTO 리스트로 변환
        List<EventResponseDTO> eventResponseDTOList = eventList.stream()
            .map(event -> new EventResponseDTO(
                event.getId(),
                event.isAllDay(),
                event.getTitle(),
                event.getStart(),
                event.getEnd(),
                event.getBackgroundColor(),
                event.getTextColor()))
            .toList();

        return new EventListResponseDTO(eventResponseDTOList);
    }

    public EventListResponseDTO getTeamCalender(Long teamPk){
        List<PersonalTimetable> personalTimetableList = getAllPersonalTimetableByTeamTimetable(teamPk);
        List<Event> eventList = new ArrayList<>();

        for (PersonalTimetable personalTimetable : personalTimetableList) {
            List<Event> personalEventList = eventRepository.findEventsByPersonalTimetable(
                personalTimetable);
            eventList.addAll(personalEventList);
        }

        List<EventResponseDTO> eventResponseDTOList = eventList.stream()
            .map(event -> new EventResponseDTO(
                event.getId(),
                event.isAllDay(),
                event.getTitle(),
                event.getStart(),
                event.getEnd(),
                event.getBackgroundColor(),
                event.getTextColor()))
            .toList();
        return new EventListResponseDTO(eventResponseDTOList);
    }
    @Transactional
    public void deletePersonalTimetable(Long teamPk, String personalName) {
        List<PersonalTimetable> personalTimetableList = getAllPersonalTimetableByTeamTimetable(teamPk);

        // personalName과 일치하는 PersonalTimetable 객체를 찾음
        PersonalTimetable personalTimetable = personalTimetableList.stream()
            .filter(pt -> pt.getName().equals(personalName))
            .findFirst()
            .orElse(null);

        if (personalTimetable == null) {
            return;
        }
        eventRepository.deleteByPersonalTimetable(personalTimetable);
        personalTimetableRepository.delete(personalTimetable);
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
