package fourmation.CommonClock.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamLoginResponse {
    private Long teamId;

    @Builder
    public TeamLoginResponse(Long teamId) {
        this.teamId = teamId;
    }
}
