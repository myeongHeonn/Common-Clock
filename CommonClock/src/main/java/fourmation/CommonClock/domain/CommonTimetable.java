package fourmation.CommonClock.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "common_id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "monday", nullable = false)
    private boolean[] monday = new boolean[24];

    @Column(name = "tuesday", nullable = false)
    private boolean[] tuesday = new boolean[24];

    @Column(name = "wednesday", nullable = false)
    private boolean[] wednesday = new boolean[24];

    @Column(name = "thursday", nullable = false)
    private boolean[] thursday = new boolean[24];

    @Column(name = "friday", nullable = false)
    private boolean[] friday = new boolean[24];

    @Column(name = "saturday", nullable = false)
    private boolean[] saturday = new boolean[24];

    @Column(name = "sunday", nullable = false)
    private boolean[] sunday = new boolean[24];

    @Builder
    public CommonTimetable(Team team, boolean[] monday, boolean[] tuesday, boolean[] wednesday, boolean[] thursday, boolean[] friday, boolean[] saturday, boolean[] sunday) {
        this.team = team;
        this.monday = monday != null ? monday : new boolean[24];
        this.tuesday = tuesday != null ? tuesday : new boolean[24];
        this.wednesday = wednesday != null ? wednesday : new boolean[24];
        this.thursday = thursday != null ? thursday : new boolean[24];
        this.friday = friday != null ? friday : new boolean[24];
        this.saturday = saturday != null ? saturday : new boolean[24];
        this.sunday = sunday != null ? sunday : new boolean[24];

        if (monday == null) Arrays.fill(this.monday, false);
        if (tuesday == null) Arrays.fill(this.tuesday, false);
        if (wednesday == null) Arrays.fill(this.wednesday, false);
        if (thursday == null) Arrays.fill(this.thursday, false);
        if (friday == null) Arrays.fill(this.friday, false);
        if (saturday == null) Arrays.fill(this.saturday, false);
        if (sunday == null) Arrays.fill(this.sunday, false);
    }
}
