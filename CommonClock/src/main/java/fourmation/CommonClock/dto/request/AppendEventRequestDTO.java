package fourmation.CommonClock.dto.request;

import lombok.Getter;

@Getter
public class AppendEventRequestDTO {
    private Long teamPk;
    private String personalName;
    private boolean allDay;
    private String title;
    private String start;
    private String end;
    private String backgroundColor;
    private String textColor;
}
