package fourmation.CommonClock.controller;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.dto.request.AddPersonalTimetableRequest;
import fourmation.CommonClock.dto.response.PersonalTimetableResponse;
import fourmation.CommonClock.service.PersonalTimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PersonalTimetableController {

    private final PersonalTimetableService personalTimetableService;

    @PostMapping("/personal")
    public ResponseEntity<PersonalTimetable> addPersonalTimetable(@RequestBody AddPersonalTimetableRequest request) {
        PersonalTimetable savedPersonalTimetable = personalTimetableService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonalTimetable);
    }

    @GetMapping("/personal/{teamId}")
    public ResponseEntity<List<PersonalTimetableResponse>> findAllPersonalTimetable(@PathVariable Long teamId) {
        List<PersonalTimetableResponse> timetables = personalTimetableService.findByTeamId(teamId)
                .stream()
                .map(PersonalTimetableResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(timetables);
    }
}
