package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.domain.TeamTimetable;
import fourmation.CommonClock.dto.request.AddTeamRequest;
import fourmation.CommonClock.dto.request.TeamLoginRequest;
import fourmation.CommonClock.dto.response.TeamLoginResponse;
import fourmation.CommonClock.repository.TeamRepository;
import fourmation.CommonClock.repository.TeamTimetableRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamTimetableRepository teamTimetableRepository;

    public Long save(AddTeamRequest dto) {
        return teamRepository.save(Team.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .build()).getId();
    }

    public TeamLoginResponse login(TeamLoginRequest dto) {
        Team team = teamRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("not found: " + dto.getLoginId()));

        if (!team.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("아이디와 비밀번호가 일치하지 않습니다.");
        }

        return TeamLoginResponse.builder().teamId(team.getId()).build();
    }

    public void createTeamTimetable(Long teamPk){
        Team team = findTeam(teamPk);
        TeamTimetable teamTimetable = new TeamTimetable(null, team);
        teamTimetableRepository.save(teamTimetable);
    }

    private Team findTeam(Long teamPk){
        return teamRepository.findById(teamPk).orElseThrow(()->new NoSuchElementException("teamPk값 없음"));
    }
}
