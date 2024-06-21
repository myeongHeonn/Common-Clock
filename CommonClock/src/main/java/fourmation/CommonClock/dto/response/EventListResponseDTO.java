package fourmation.CommonClock.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventListResponseDTO {
    private List<EventResponseDTO> eventList;
}
