package fourmation.CommonClock.controller;

import fourmation.CommonClock.common.ApiResponse;
import fourmation.CommonClock.common.ApiResponseBody.SuccessBody;
import fourmation.CommonClock.common.ApiResponseGenerator;
import fourmation.CommonClock.common.SuccessMessage;
import fourmation.CommonClock.dto.request.AddTeamRequest;
import fourmation.CommonClock.dto.request.TeamLoginRequest;
import fourmation.CommonClock.dto.response.AddTeamResponse;
import fourmation.CommonClock.dto.response.TeamLoginResponse;
import fourmation.CommonClock.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/signup")
    public ApiResponse<SuccessBody<AddTeamResponse>> signup(@RequestBody AddTeamRequest request) {
        Long teamPk = teamService.save(request);
        teamService.createTeamTimetable(teamPk);
        AddTeamResponse addTeamResponse = new AddTeamResponse(teamPk);
        return ApiResponseGenerator.success(addTeamResponse, HttpStatus.OK, SuccessMessage.CREATE);
    }

    @PostMapping("/login")
    public ApiResponse<SuccessBody<TeamLoginResponse>> login(@RequestBody TeamLoginRequest request) {
        TeamLoginResponse teamLoginResponse = teamService.login(request);
        return ApiResponseGenerator.success(teamLoginResponse, HttpStatus.OK, SuccessMessage.LOGIN);
    }

}
