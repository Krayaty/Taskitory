package de.krayadev.domain.aggregates.projectAggregate.valueObjects;

import lombok.*;

import javax.persistence.Embeddable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@ToString
public final class Sprint {

    @NonNull
    private Timestamp startOfSprint = Timestamp.valueOf(LocalDateTime.now());

    @NonNull
    private Timestamp endOfSprint;

    public Sprint(Timestamp endOfSprint) {
        if (endOfSprint == null) {
            int fiveMinutes = 1000 * 60 * 5;
            this.endOfSprint = new Timestamp(startOfSprint.getTime() + fiveMinutes);
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
        if (duration.compareTo(Duration.ofSeconds(30)) > 0)
            throw new IllegalArgumentException("Duration must be at least 30 seconds");

        this.endOfSprint = new Timestamp(startOfSprint.getTime() + duration.toMillis());


        this.endOfSprint = endOfSprint;
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
}
