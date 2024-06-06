package fourmation.CommonClock.controller;

import fourmation.CommonClock.domain.CommonTimetable;
import fourmation.CommonClock.service.CommonTimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonTimetableController {

    private final CommonTimetableService commonTimetableService;

    @PostMapping("/common/{teamId}")
    public ResponseEntity<CommonTimetable> addCommonTimetable(@PathVariable Long teamId) {
        CommonTimetable savedCommonTimetable = commonTimetableService.save(teamId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommonTimetable);
    }

}
