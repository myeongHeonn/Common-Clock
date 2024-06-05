package fourmation.CommonClock.dto;

import fourmation.CommonClock.domain.PersonalTimetable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPersonalTimetableRequest {

    private String name;
    private boolean[] monday = new boolean[24];
    private boolean[] tuesday = new boolean[24];
    private boolean[] wednesday = new boolean[24];
    private boolean[] thursday = new boolean[24];
    private boolean[] friday = new boolean[24];
    private boolean[] saturday = new boolean[24];
    private boolean[] sunday = new boolean[24];

    public PersonalTimetable toEntity() {
        return PersonalTimetable.builder()
                .name(name)
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
