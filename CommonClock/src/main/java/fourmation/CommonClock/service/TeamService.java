package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}
