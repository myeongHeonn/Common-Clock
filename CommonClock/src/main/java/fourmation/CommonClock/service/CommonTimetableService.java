package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.CommonTimetable;
import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.repository.CommonTimetableRepository;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CommonTimetableService {

    private final PersonalTimetableRepository personalTimetableRepository;
    private final CommonTimetableRepository commonTimetableRepository;
    private final TeamRepository teamRepository;

    public CommonTimetable save(Long teamId) {
        List<PersonalTimetable> timetables = personalTimetableRepository.findByTeamId(teamId);
        if (timetables.isEmpty()) {
            throw new IllegalArgumentException("No personal timetables found for team ID: " + teamId);
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + teamId));

        CommonTimetable commonTimetable = calculateCommonTimetable(timetables, team);

        return commonTimetableRepository.save(commonTimetable);
    }

    public CommonTimetable findById(Long id) {
        return commonTimetableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    private CommonTimetable calculateCommonTimetable(List<PersonalTimetable> timetables, Team team) {
        boolean[] commonMonday = new boolean[24];
        boolean[] commonTuesday = new boolean[24];
        boolean[] commonWednesday = new boolean[24];
        boolean[] commonThursday = new boolean[24];
        boolean[] commonFriday = new boolean[24];
        boolean[] commonSaturday = new boolean[24];
        boolean[] commonSunday = new boolean[24];

        IntStream.range(0,24).forEach(hour -> {
            commonMonday[hour] = timetables.stream().anyMatch(t -> t.getMonday()[hour]);
            commonTuesday[hour] = timetables.stream().anyMatch(t -> t.getTuesday()[hour]);
            commonWednesday[hour] = timetables.stream().anyMatch(t -> t.getWednesday()[hour]);
            commonThursday[hour] = timetables.stream().anyMatch(t -> t.getThursday()[hour]);
            commonFriday[hour] = timetables.stream().anyMatch(t -> t.getFriday()[hour]);
            commonSaturday[hour] = timetables.stream().anyMatch(t -> t.getSaturday()[hour]);
            commonSunday[hour] = timetables.stream().anyMatch(t -> t.getSunday()[hour]);
        });

        return CommonTimetable.builder()
                .team(team)
                .monday(commonMonday)
                .tuesday(commonTuesday)
                .wednesday(commonWednesday)
                .thursday(commonThursday)
                .friday(commonFriday)
                .saturday(commonSaturday)
                .sunday(commonSunday)
                .build();
    }
}
