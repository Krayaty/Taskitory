package de.krayadev.domain.aggregates.taskAggregate.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@ToString
public final class Priority {

    public static final Priority NONE = new Priority();

    @NonNull
    private final int value;

    public Priority() {
        this.value = 0;
    }

    public Priority(@NonNull int priority) {
        if (priority < 0 || priority > 20)
            throw new IllegalArgumentException("Priority must be between 0 and 20");

        this.value = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Priority priority = (Priority) o;
        return value == priority.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
