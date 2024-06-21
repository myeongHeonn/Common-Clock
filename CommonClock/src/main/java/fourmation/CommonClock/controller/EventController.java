package fourmation.CommonClock.controller;

import fourmation.CommonClock.common.ApiResponse;
import fourmation.CommonClock.common.ApiResponseBody.SuccessBody;
import fourmation.CommonClock.common.ApiResponseGenerator;
import fourmation.CommonClock.common.SuccessMessage;
import fourmation.CommonClock.dto.request.AppendEventRequestDTO;
import fourmation.CommonClock.dto.response.AppendEventResponseDTO;
import fourmation.CommonClock.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/personal/event")
    public ApiResponse<SuccessBody<AppendEventResponseDTO>> appendEvent(@Validated @RequestBody
        AppendEventRequestDTO appendEventRequestDTO){
        AppendEventResponseDTO appendEventResponseDTO = eventService.appendEvent(appendEventRequestDTO);
        return ApiResponseGenerator.success(appendEventResponseDTO, HttpStatus.OK, SuccessMessage.CREATE);
    }
}
