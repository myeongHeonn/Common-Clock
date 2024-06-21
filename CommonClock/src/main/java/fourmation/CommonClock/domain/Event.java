package fourmation.CommonClock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private PersonalTimetable personalTimetable;

    @Column(name = "all_day", nullable = false)
    private boolean allDay;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start", nullable = false)
    private String start;

    @Column(name = "end", nullable = false)
    private String end;

    @Column(name = "background_color", nullable = false)
    private String backgroundColor;

    @Column(name = "text_color", nullable = false)
    private String textColor;

    public Event(Long id, PersonalTimetable personalTimetable, boolean allDay, String title,
        String start, String end, String backgroundColor, String textColor) {
        this.id = id;
        this.personalTimetable = personalTimetable;
        this.allDay = allDay;
        this.title = title;
        this.start = start;
        this.end = end;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
}