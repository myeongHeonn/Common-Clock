package fourmation.CommonClock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponseDTO {
    private boolean allDay;
    private String title;
    private String start;
    private String end;
    private String backgroundColor;
    private String textColor;

}
