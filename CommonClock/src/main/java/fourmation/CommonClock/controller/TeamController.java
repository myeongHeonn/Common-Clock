package fourmation.CommonClock.controller;

import fourmation.CommonClock.domain.Team;
import fourmation.CommonClock.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return teams;
    }
}
