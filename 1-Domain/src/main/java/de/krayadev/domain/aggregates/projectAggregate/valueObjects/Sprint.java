package de.krayadev.domain.aggregates.projectAggregate.valueObjects;

import lombok.*;

import javax.persistence.Embeddable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Getter
@ToString
public final class Sprint {

    @NonNull
    private final Timestamp startOfSprint;

    @NonNull
    private final Timestamp endOfSprint;

    protected Sprint() {
        this.startOfSprint = Timestamp.valueOf(LocalDateTime.now());
        this.endOfSprint = Timestamp.valueOf(LocalDateTime.now().plusWeeks(2));
    }
    public Sprint(Timestamp endOfSprint) {
        this.startOfSprint = Timestamp.valueOf(LocalDateTime.now());

        if (endOfSprint == null) {
            int fiveMinutes = 1000 * 60 * 5;
            this.endOfSprint = new Timestamp(startOfSprint.getTime() + fiveMinutes);
            return;
        }

        if (endOfSprint.before(startOfSprint) || endOfSprint.equals(startOfSprint))
            throw new IllegalArgumentException("End of sprint must be after start of sprint");

        this.endOfSprint = endOfSprint;
    }

    public Sprint(@NonNull Timestamp startOfSprint, Timestamp endOfSprint) {
        this.startOfSprint = startOfSprint;

        if (endOfSprint == null) {
            int fiveMinutes = 1000 * 60 * 5;
            this.endOfSprint = new Timestamp(startOfSprint.getTime() + fiveMinutes);

        } else {
            if (endOfSprint.before(startOfSprint) || endOfSprint.equals(startOfSprint))
                throw new IllegalArgumentException("End of sprint must be after start of sprint");

            this.endOfSprint = endOfSprint;
        }
    }

    public Sprint(@NonNull Duration duration) {
        this.startOfSprint = Timestamp.valueOf(LocalDateTime.now());

        if (duration.compareTo(Duration.ofSeconds(30)) > 0)
            throw new IllegalArgumentException("Duration must be at least 30 seconds");

        this.endOfSprint = new Timestamp(this.startOfSprint.getTime() + duration.toMillis());
    }

    public Sprint(@NonNull Timestamp startOfSprint, @NonNull Duration duration) {
        this.startOfSprint = startOfSprint;

        if (duration.compareTo(Duration.ofSeconds(30)) > 0)
            throw new IllegalArgumentException("Duration must be at least 30 seconds");

        this.endOfSprint = new Timestamp(startOfSprint.getTime() + duration.toMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return startOfSprint.equals(sprint.startOfSprint) &&
                Objects.equals(endOfSprint, sprint.endOfSprint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startOfSprint, endOfSprint);
    }

    public boolean isOver() {
        return endOfSprint.before(Timestamp.valueOf(LocalDateTime.now()));
    }
}