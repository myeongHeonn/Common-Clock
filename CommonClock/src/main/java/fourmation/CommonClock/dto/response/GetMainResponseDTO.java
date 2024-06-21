package fourmation.CommonClock.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMainResponseDTO {
    private int userNum;
    private List<String> userNames;
}
