package de.krayadev.domain.aggregates.taskAggregate.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
@Getter
@ToString
public final class Complexity {

    public static final Complexity NONE = new Complexity(0);

    @NonNull
    private int value;

    public Complexity(@NonNull int value) {
        if (value < 0 || value > 20)
            throw new IllegalArgumentException("Complexity must be between 0 and 20");

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Complexity that = (Complexity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
