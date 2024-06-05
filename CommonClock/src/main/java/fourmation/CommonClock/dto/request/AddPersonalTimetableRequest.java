package fourmation.CommonClock.dto.request;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.domain.Team;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPersonalTimetableRequest {

    private String name;
    private Long teamId;
    private boolean[] monday = new boolean[24];
    private boolean[] tuesday = new boolean[24];
    private boolean[] wednesday = new boolean[24];
    private boolean[] thursday = new boolean[24];
    private boolean[] friday = new boolean[24];
    private boolean[] saturday = new boolean[24];
    private boolean[] sunday = new boolean[24];

    public PersonalTimetable toEntity(Team team) {
        return PersonalTimetable.builder()
                .name(name)
                .team(team)
                .monday(monday)
                .tuesday(tuesday)
                .wednesday(wednesday)
                .thursday(thursday)
                .friday(friday)
                .saturday(saturday)
                .sunday(sunday)
                .build();
    }

}
