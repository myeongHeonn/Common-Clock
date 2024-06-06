package fourmation.CommonClock.dto.response;

import fourmation.CommonClock.domain.CommonTimetable;
import lombok.Getter;

@Getter
public class CommonTimetableResponse {

    private final boolean[] monday;
    private final boolean[] tuesday;
    private final boolean[] wednesday;
    private final boolean[] thursday;
    private final boolean[] friday;
    private final boolean[] saturday;
    private final boolean[] sunday;

    public CommonTimetableResponse(CommonTimetable commonTimetable) {
        this.monday = commonTimetable.getMonday();
        this.tuesday = commonTimetable.getTuesday();
        this.wednesday = commonTimetable.getWednesday();
        this.thursday = commonTimetable.getThursday();
        this.friday = commonTimetable.getFriday();
        this.saturday = commonTimetable.getSaturday();
        this.sunday = commonTimetable.getSunday();
    }
}
