package de.krayadev.domain.aggregates.taskAggregate.valueObjects;

import de.krayadev.domain.aggregates.taskAggregate.entities.task.TaskStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Getter
@ToString
public final class TaskLifecycle {

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    private final Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());

    private Timestamp completedOn;

    public TaskLifecycle() {
        this.completedOn = null;
    }

    public TaskLifecycle(Timestamp completedOn) {
        if (completedOn.before(createdOn) || completedOn.equals(createdOn))
            throw new IllegalArgumentException("Task cannot be completed before or as it was created");

        this.completedOn = completedOn;
        this.status = TaskStatus.DONE;
    }

    private void complete() {
        this.completedOn = Timestamp.valueOf(LocalDateTime.now());
        this.status = TaskStatus.DONE;
    }

    private void reopen() {
        this.completedOn = null;
    }

    public boolean inStatus(TaskStatus status) {
        return this.status == status;
    }

    public boolean isCompleted() {
        return this.completedOn != null;
    }

    public boolean isActive() {
        return !isCompleted();
    }

    public void change(@NonNull TaskStatus status) {
        if(status == this.status)
            throw new IllegalArgumentException("Task status cannot be changed to the same status");

        if(status == TaskStatus.DONE) {
            complete();
            return;
        }

        if (this.status == TaskStatus.DONE)
            this.reopen();

        this.status = status;
    }

    public Duration getProcessingDuration() {
        if(this.isActive())
            throw new IllegalStateException("Task is not completed yet");

        return Duration.between(this.createdOn.toLocalDateTime(), this.completedOn.toLocalDateTime());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskLifecycle that = (TaskLifecycle) o;
        return createdOn.equals(that.createdOn) &&
                Objects.equals(completedOn, that.completedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, completedOn);
    }
}
