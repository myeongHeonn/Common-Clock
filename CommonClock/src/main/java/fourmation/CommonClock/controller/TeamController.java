package fourmation.CommonClock.controller;

import fourmation.CommonClock.dto.request.AddTeamRequest;
import fourmation.CommonClock.dto.request.TeamLoginRequest;
import fourmation.CommonClock.dto.response.TeamLoginResponse;
import fourmation.CommonClock.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody AddTeamRequest request) {
        Long teamPk = teamService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamPk);
    }

//    @GetMapping("/login")
//    public ResponseEntity<TeamLoginResponse> login(@RequestBody TeamLoginRequest request) {
//        TeamLoginResponse teamLoginResponse = teamService.login(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(teamLoginResponse);
//    }
}
