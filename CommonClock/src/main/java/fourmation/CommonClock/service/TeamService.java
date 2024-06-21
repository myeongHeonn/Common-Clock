package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.dto.request.AddTeamRequest;
import fourmation.CommonClock.dto.request.TeamLoginRequest;
import fourmation.CommonClock.dto.response.TeamLoginResponse;
import fourmation.CommonClock.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public Long save(AddTeamRequest dto) {
        return teamRepository.save(Team.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .build()).getId();
    }
}
