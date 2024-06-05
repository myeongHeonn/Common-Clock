package fourmation.CommonClock.controller;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.dto.AddPersonalTimetableRequest;
import fourmation.CommonClock.service.PersonalTimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PersonalTimetableController {

    private final PersonalTimetableService personalTimetableService;

    @PostMapping("/personal")
    public ResponseEntity<PersonalTimetable> addPersonalTimetable(@RequestBody AddPersonalTimetableRequest request) {
        PersonalTimetable savedPersonalTimetable = personalTimetableService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonalTimetable);
    }
}
