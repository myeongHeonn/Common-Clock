package fourmation.CommonClock.controller;

import fourmation.CommonClock.common.ApiResponse;
import fourmation.CommonClock.common.ApiResponseBody.SuccessBody;
import fourmation.CommonClock.common.ApiResponseGenerator;
import fourmation.CommonClock.common.SuccessMessage;
import fourmation.CommonClock.dto.request.AppendPersonalTimeTableRequestDTO;
import fourmation.CommonClock.dto.response.AppendPersonalTimeTableResponseDTO;
import fourmation.CommonClock.dto.response.EventListResponseDTO;
import fourmation.CommonClock.dto.response.GetMainResponseDTO;
import fourmation.CommonClock.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @PostMapping("/personal")
    public ApiResponse<SuccessBody<AppendPersonalTimeTableResponseDTO>> appendPersonalTimeTable(@Validated @RequestBody
    AppendPersonalTimeTableRequestDTO appendPersonalTimeTableRequestDTO){
        Long personalPk = timetableService.createPersonalTimeTable(
            appendPersonalTimeTableRequestDTO.getTeamPk());

        AppendPersonalTimeTableResponseDTO appendPersonalTimeTableResponseDTO = new AppendPersonalTimeTableResponseDTO(personalPk);

        return ApiResponseGenerator.success(appendPersonalTimeTableResponseDTO,HttpStatus.OK, SuccessMessage.CREATE);
    }
    @DeleteMapping("/personal/{teamPk}/{personalName}")
    public ApiResponse<SuccessBody<Void>> deletePersonalTimeTable(@PathVariable Long teamPk, @PathVariable String personalName){
        timetableService.deletePersonalTimetable(teamPk, personalName);
        return ApiResponseGenerator.success(HttpStatus.OK, SuccessMessage.DELETE);
    }

    @GetMapping("/personal/{teamPk}")
    public ApiResponse<SuccessBody<GetMainResponseDTO>> getMain(@PathVariable Long teamPk){
        GetMainResponseDTO getMainResponseDTO = timetableService.getMain(teamPk);
        return ApiResponseGenerator.success(getMainResponseDTO, HttpStatus.OK, SuccessMessage.CREATE);
    }

    @GetMapping("/personal/{teamPk}/{personalName}")
    public ApiResponse<SuccessBody<EventListResponseDTO>> getPersonalCalendar(
        @PathVariable Long teamPk, @PathVariable String personalName
    ){
        EventListResponseDTO eventListResponseDTO = timetableService.getPersonalCalender(teamPk, personalName);
        return ApiResponseGenerator.success(eventListResponseDTO, HttpStatus.OK, SuccessMessage.CREATE);
    }

    @GetMapping("/team/{teamPk}")
    public ApiResponse<SuccessBody<EventListResponseDTO>> getTeamCalendar(@PathVariable Long teamPk){
        EventListResponseDTO eventListResponseDTO = timetableService.getTeamCalender(teamPk);
        return ApiResponseGenerator.success(eventListResponseDTO, HttpStatus.OK, SuccessMessage.CREATE);
    }
}
