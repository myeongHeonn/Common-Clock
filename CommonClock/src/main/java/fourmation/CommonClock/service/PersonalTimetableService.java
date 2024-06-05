package fourmation.CommonClock.service;

import fourmation.CommonClock.domain.PersonalTimetable;
import fourmation.CommonClock.dto.AddPersonalTimetableRequest;
import fourmation.CommonClock.repository.PersonalTimetableRepository;
import fourmation.CommonClock.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PersonalTimetableService {

    private final PersonalTimetableRepository personalTimetableRepository;

    public PersonalTimetable save(AddPersonalTimetableRequest request) {
        return personalTimetableRepository.save(request.toEntity());
    }
}
