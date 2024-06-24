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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            // Try parsing as ISO_OFFSET_DATE_TIME (datetime format)
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (Exception e) {
            // Fallback to parsing as ISO_LOCAL_DATE (date format)
            LocalDate date = LocalDate.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE);
            return date.atStartOfDay(); // Set time to start of the day
        }
    }

    public EventListResponseDTO getTeamCalendar(Long teamPk) {
        String date = LocalDate.now().toString();
        LocalDate currentDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime startOfDay = currentDate.atTime(9, 0);
        LocalDate endOfWeek = currentDate.with(DayOfWeek.SATURDAY);
        LocalDateTime endOfDay = endOfWeek.atTime(23, 59, 59);

        List<PersonalTimetable> personalTimetableList = getAllPersonalTimetableByTeamTimetable(teamPk);
        List<Event> eventList = new ArrayList<>();

        for (PersonalTimetable personalTimetable : personalTimetableList) {
            List<Event> personalEventList = eventRepository.findEventsByPersonalTimetable(personalTimetable);
            for (Event event : personalEventList) {
                LocalDateTime eventEnd = parseDateTime(event.getEnd());
                if (!eventEnd.isBefore(startOfDay)) {
                    eventList.add(event);
                }
            }
        }

        eventList.sort((e1, e2) -> parseDateTime(e1.getStart())
            .compareTo(parseDateTime(e2.getStart())));

        List<EventResponseDTO> teamEventList = new ArrayList<>();

        LocalDateTime currentTime = startOfDay;

        for (Event event : eventList) {
            LocalDateTime eventStart = parseDateTime(event.getStart());
            LocalDateTime eventEnd = parseDateTime(event.getEnd());

            if (eventStart.isAfter(currentTime)) {
                teamEventList.add(new EventResponseDTO(
                    null, false, "No Event", currentTime.toString(), eventStart.toString(), "#00e676", "#FFFFFF"
                ));
            }

//            teamEventList.add(new EventResponseDTO(
//                event.getId(), event.isAllDay(), event.getTitle(), event.getStart(), event.getEnd(),
//                "#f44336", event.getTextColor()
//            ));

            currentTime = eventEnd.isAfter(currentTime) ? eventEnd : currentTime;
        }

        if (currentTime.isBefore(endOfDay)) {
            teamEventList.add(new EventResponseDTO(
                null, false, "No Event", currentTime.toString(), endOfDay.toString(), "#00e676", "#FFFFFF"
            ));
        }

        return new EventListResponseDTO(teamEventList);
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
