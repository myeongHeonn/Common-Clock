package fourmation.CommonClock.controller;

import fourmation.CommonClock.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
}
