package fourmation.CommonClock.dto.response;

import fourmation.CommonClock.domain.PersonalTimetable;
import lombok.Getter;

@Getter
public class PersonalTimetableResponse {

    private final String name;
    private final boolean[] monday;
    private final boolean[] tuesday;
    private final boolean[] wednesday;
    private final boolean[] thursday;
    private final boolean[] friday;
    private final boolean[] saturday;
    private final boolean[] sunday;

    public PersonalTimetableResponse(PersonalTimetable personalTimetable) {
        this.name = personalTimetable.getName();
        this.monday = personalTimetable.getMonday();
        this.tuesday = personalTimetable.getTuesday();
        this.wednesday = personalTimetable.getWednesday();
        this.thursday = personalTimetable.getThursday();
        this.friday = personalTimetable.getFriday();
        this.saturday = personalTimetable.getSaturday();
        this.sunday = personalTimetable.getSunday();
    }
}
