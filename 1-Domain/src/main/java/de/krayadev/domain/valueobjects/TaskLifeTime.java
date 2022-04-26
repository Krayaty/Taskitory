package de.krayadev.domain.valueobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
@Getter
@ToString
public final class TaskLifeTime {

    private final Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());

    private Timestamp completedOn;

    public TaskLifeTime(Timestamp completedOn) {
        if (completedOn.before(createdOn) || completedOn.equals(createdOn))
            throw new IllegalArgumentException("Task cannot be completed before or as it was created");

        this.completedOn = completedOn;
    }

    public boolean isCompleted() {
        return this.completedOn != null;
    }

    public boolean isActive() {
        return !isCompleted();
    }

    public void complete() {
        this.completedOn = Timestamp.valueOf(LocalDateTime.now());
    }

    public void reopen() {
        this.completedOn = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskLifeTime that = (TaskLifeTime) o;
        return createdOn.equals(that.createdOn) &&
                Objects.equals(completedOn, that.completedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, completedOn);
    }
}
